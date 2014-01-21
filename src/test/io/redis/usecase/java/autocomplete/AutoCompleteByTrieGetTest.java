package io.redis.usecase.java.autocomplete;

import static org.junit.Assert.*;
import io.redis.usecase.java.JedisHelper;
import io.redis.usecase.java.autocomplete.AutoCompleteByTrie;

import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class AutoCompleteByTrieGetTest {
    JedisHelper jedisHelper = JedisHelper.getInstance();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        jedisHelper.getConnection();
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void testGetPhrase() {
        AutoCompleteByTrie autoComplete = new AutoCompleteByTrie(jedisHelper);
        List<String> phrases = null;
        phrases = autoComplete.getPhrase("4");
        assertNotNull(phrases);

        phrases = autoComplete.getPhrase("가");
        assertNotNull(phrases);
        System.out.println(phrases);

        phrases = autoComplete.getPhrase("강");
        assertNotNull(phrases);
        System.out.println(phrases);

//        autoComplete.releaseConnection();

        System.out.println(phrases);
    }

    @Test
  @Ignore
    public void testGetPhraseLoop() {
        AutoCompleteByTrie autoComplete = new AutoCompleteByTrie(jedisHelper);
        List<String> phrases = null;
        Random r = new Random();

        for (int i = 0; i < 100000; i++) {
            char c = (char) (r.nextInt(26) + 'a');
            phrases = autoComplete.getPhrase(String.valueOf(c));
        }
        assertNotNull(phrases);

        System.out.println(phrases);
    }
}
