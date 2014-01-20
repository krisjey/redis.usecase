package net.sf.redisbook.autocomplete;

import java.util.Set;

import net.sf.redisbook.JedisHelper;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.util.MurmurHash;

public class AutoComplete {
    private final Jedis jedis;
    private static final int SEED_MURMURHASH = 0x1234ABCD;

    public AutoComplete(JedisHelper jedisHelper) {
        jedis = jedisHelper.getConnection();
    }

    public void addPhrase(String phrase, int score) {
        // TODO Try 처리하기.
        Pipeline pipeline = jedis.pipelined();

        String phraseKey = Long.toString(MurmurHash.hash64A(phrase.getBytes(), SEED_MURMURHASH));

        KoreanSoundExtractor koreanSoundExtractor = new KoreanSoundExtractor();
        String result = koreanSoundExtractor.getSoundExtractedString(phrase);

        StringBuilder builder = new StringBuilder(result.length());
        for (char element : result.toCharArray()) {
            builder.append(element);

            jedis.zadd(builder.toString(), score, phraseKey);
        }

        // set phrase
        pipeline.set(phraseKey, phrase);

        pipeline.sync();
    }

    public Set<String> getPhrase(String prefix) {
        // TODO check the number of element in the zset.
        return jedis.zrange(prefix, 0, 4);
    }
}
