package net.sf.redisbook.autocomplete;

import java.util.List;
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

    private void getIndexFromRedis() {

    }

    public Set<String> getPhrase(String prefix) {
        // TODO 하나의 zset에 가장 많이 들어있는 개수 확인하기.
        return jedis.zrange(prefix, 0, 4);
    }
}
