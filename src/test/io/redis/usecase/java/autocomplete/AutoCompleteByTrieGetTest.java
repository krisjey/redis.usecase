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
	private static final int RETREVE_DATA_SIZE = 4;
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
        phrases = autoComplete.getPhrase("4", RETREVE_DATA_SIZE);
        assertNotNull(phrases);

        phrases = autoComplete.getPhrase("가", RETREVE_DATA_SIZE);
        assertNotNull(phrases);
        System.out.println(phrases);

        phrases = autoComplete.getPhrase("강", RETREVE_DATA_SIZE);
        assertNotNull(phrases);
        System.out.println(phrases);

//        autoComplete.releaseConnection();
//        System.out.println(phrases);
    }

    @Test
    public void testGetPhraseLoop() {
        AutoCompleteByTrie autoComplete = new AutoCompleteByTrie(jedisHelper);
        List<String> phrases = null;
        Random r = new Random();

        for (int i = 0; i < 100000; i++) {
            phrases = autoComplete.getPhrase(String.valueOf((char) (r.nextInt(26) + 'a')), RETREVE_DATA_SIZE);
        }
        assertNotNull(phrases);

        System.out.println(phrases);
    }
}
