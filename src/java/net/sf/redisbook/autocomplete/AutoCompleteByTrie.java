package net.sf.redisbook.autocomplete;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.sf.redisbook.JedisHelper;
import redis.clients.jedis.Jedis;

public class AutoCompleteByTrie {
    private final Jedis jedis;

    public AutoCompleteByTrie(JedisHelper jedisHelper) {
        jedis = jedisHelper.getConnection();
    }

    /**
     * Retrieve matched data from autocomplete dic.
     * @param prefix for searching
     * @return matched data by prefix
     */
    public List<String> getPhrase(String prefix) {
        KoreanSoundExtractor koreanSoundExtractor = new KoreanSoundExtractor();
        String result = koreanSoundExtractor.getSoundExtractedString(prefix);

        Set<String> phraseIds = jedis.zrange(result, 0, 4);
        if (phraseIds.size() == 0)   {
            return new ArrayList<String>();
        }
        return jedis.mget(phraseIds.toArray(new String[phraseIds.size()]));
    }

    public void releaseConnection() {
        JedisHelper.getInstance().returnResource(jedis);
    }
}
