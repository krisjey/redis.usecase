redis.usecase
=============

This Repo is several implementation of redis use case.

Currently, Autocomplete feature is pushed.

Later PV, UV feature will be pushed to this repo

## Autocomplete using TRIE data structuer.

This implementation is using trie data structure.

Few years ago, Salvatore Sanfilippo introduced about Autocomplete with Redis.

Now i need a this feature. so i tried to start java implementation.
But, result is too slow.

I digging more and more. root cause of slow is that frequent call of zrange command.

If there are too many keyword for autocomplete or too long sentences then that need a lots of zrange call.

Below is solution of single zrange call.

- Every keyword has a keyword id.
- Every trie node convert to zset.
- Each trie node's path convert to zset's key name.
- Each node has a keyword id.

Here is a code for building a trie.

        Pipeline pipeline = jedis.pipelined();
        String phraseId = Long.toString(MurmurHash.hash64A(phrase.getBytes(), SEED_MURMURHASH));

        StringBuilder builder = new StringBuilder(phrase.length());
        for (char element : phrase.toCharArray()) {
            builder.append(element);
            pipeline.zadd(builder.toString(), score, phraseId);
        }

        pipeline.set(phraseId, phrase);

        pipeline.sync();

If subset of keyword is "re" then that need a single zrange and mget call.

The read performance is over 7k on my notbook(i5-3337u) and benchmark result is here. 

"SET","27777.78"
"GET","30303.03"

