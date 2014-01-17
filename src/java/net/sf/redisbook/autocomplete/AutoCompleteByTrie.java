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
     * 주어진 Prefix에 매칭되는 데이터를 조회한다.
     * @param prefix
     * @return
     */
    public List<String> getPhrase(String prefix) {
        // TODO 하나의 zset에 가장 많이 들어있는 개수 확인하기.
        Set<String> phraseIds = jedis.zrange(prefix, 0, 4);
        if (phraseIds.size() == 0)   {
            return new ArrayList<String>();
        }
        return jedis.mget(phraseIds.toArray(new String[phraseIds.size()]));
    }

    public void releaseConnection() {
        JedisHelper.getInstance().returnResource(jedis);
    }
}
