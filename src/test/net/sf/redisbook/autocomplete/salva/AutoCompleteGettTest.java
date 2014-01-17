package net.sf.redisbook.autocomplete.salva;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.sf.redisbook.JedisHelper;
import net.sf.redisbook.autocomplete.AutoCompleteBuilderByTrie;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import redis.clients.jedis.Jedis;

public class AutoCompleteGettTest {

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
            AutoComplete autoComplete = new AutoComplete.Builder(redis, "kris", url).clear(false).populate(false).build();

            List<String> test = null;
            for (int i = 0; i < 10; i++)
                test = autoComplete.complete("4", 5);

            System.out.println(test);

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
