package io.redis.usecase.java.autocomplete.antirez;

import java.net.URL;

import redis.clients.jedis.Jedis;

/**
 * code from https://github.com/xp/jrediscomplete
 *
 * Usage examples.
 *
 * @author xp
 */
public class Usage {

    public static void main(final String[] args) throws Exception {
        Jedis redis = new Jedis("localhost", 6379);
        URL url = new URL("http://antirez.com/misc/female-names.txt");

        AutoComplete autoComplete = new AutoComplete.Builder(redis, "test", url).clear(false).populate(false).build();

        System.out.println(autoComplete.complete("ma", 10));
    }
}
