redis.usecase
=============
> Use cases of Redis.

This repository has Redis use cases including Autocomplete feature. PV and UV features will be pushed later.


## A performance issue of Autocomplete with TRIE data structure

These use cases are using TRIE data structure which is introduced from [Salvatore Sanfilippo few years ago](http://oldblog.antirez.com/post/autocomplete-with-redis.html). When I made a java implementation [first time](https://github.com/krisjey/redis.usecase/blob/master/src/test/io/redis/usecase/java/autocomplete/antirez/AutoCompleteGetTest.java), it's too slow to use. I've digged more and found out that the problem was frequent calling of zrange command. If there're many keywords or long sentences to autocomplete then they call a lot of zrange command.


## How to solve above problem?

 - Every keyword has a keyword id.
 - Every TRIE node should be converted to zset.
 - Each TRIE node's path should be converted to zset's key name.
 - Each node has a keyword id.


### Code for building TRIE
```java
Pipeline pipeline = jedis.pipelined();
String phraseId = Long.toString(MurmurHash.hash64A(phrase.getBytes(), SEED_MURMURHASH));

StringBuilder builder = new StringBuilder(phrase.length());
for (char element : phrase.toCharArray()) {
	builder.append(element);
	pipeline.zadd(builder.toString(), score, phraseId);
}

pipeline.set(phraseId, phrase);

pipeline.sync();
```

If a keyword has "re" as a its subset then that means it needs to call each zrange and mget seperately.

Here is a code to retreive data.
```java
public List<String> getPhrase(String prefix) {
	Set<String> phraseIds = jedis.zrange(prefix, 0, 4);
	if (phraseIds.size() == 0)   {
		return new ArrayList<String>();
	}
	return jedis.mget(phraseIds.toArray(new String[phraseIds.size()]));
}
```

The performance is over 7000/sec on my laptop(i5-3337u) and the benchmark result on my laptop is below.
```
"PING_INLINE","31055.90"
"PING_BULK","31250.00"
"SET","29761.90"
"GET","29498.53"
"INCR","30395.14"
"LPUSH","30581.04"
"LPOP","28571.43"
"SADD","29325.51"
"SPOP","31545.74"
"LPUSH (needed to benchmark LRANGE)","29411.76"
"LRANGE_100 (first 100 elements)","19801.98"
"LRANGE_300 (first 300 elements)","10040.16"
"LRANGE_500 (first 450 elements)","6747.64"
"LRANGE_600 (first 600 elements)","5830.90"
"MSET (10 keys)","23923.44"
```
