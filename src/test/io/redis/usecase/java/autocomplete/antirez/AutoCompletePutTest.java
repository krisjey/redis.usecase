package io.redis.usecase.java.autocomplete.antirez;

import io.redis.usecase.java.autocomplete.antirez.AutoComplete;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import redis.clients.jedis.Jedis;

public class AutoCompletePutTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        try {
            Jedis redis = new Jedis("localhost", 6379);
            URL url;
            url = new URL("http://antirez.com/misc/female-names.txt");
            AutoComplete autoComplete = new AutoComplete.Builder(redis, "kris", url).clear(false).populate(true).build();
        }
        catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
