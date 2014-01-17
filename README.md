redis.usecase
=============
> Use cases of Redis.

This repository has Redis use cases including Autocomplete feature. PV and UV features will be pushed later.


## A performance issue of Autocomplete with TRIE data structure

This use cases are using TRIE data structure which is introduced from Salvatore Sanfilippo few years ago. When I made a java implementation first time, it's too slow to use. I've digged more and found out that the problem was frequent calling of zrange command. If there're many keywords or long sentences to autocomplete then they call a lot of zrange.


## How to solve above problem?

 - Every keyword has a keyword id.
 - Every trie node should be converted to zset.
 - Each trie node's path should be converted to zset's key name.
 - Each node has a keyword id.


### Code

        Pipeline pipeline = jedis.pipelined();
        String phraseId = Long.toString(MurmurHash.hash64A(phrase.getBytes(), SEED_MURMURHASH));

        StringBuilder builder = new StringBuilder(phrase.length());
        for (char element : phrase.toCharArray()) {
            builder.append(element);
            pipeline.zadd(builder.toString(), score, phraseId);
        }

        pipeline.set(phraseId, phrase);

        pipeline.sync();

If a keyword has "re" as a its subset then that means it needs to call each zrange and mget seperately.

Here is a code to retreive data.

	public List<String> getPhrase(String prefix) {
        Set<String> phraseIds = jedis.zrange(prefix, 0, 4);
        if (phraseIds.size() == 0)   {
            return new ArrayList<String>();
        }
        return jedis.mget(phraseIds.toArray(new String[phraseIds.size()]));
    }

The read performance is over 7k on my laptop(i5-3337u) and the benchmark result is here.

"SET","27777.78"
"GET","30303.03"

