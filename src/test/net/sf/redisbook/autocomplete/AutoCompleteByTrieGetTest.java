package net.sf.redisbook.autocomplete;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import net.sf.redisbook.JedisHelper;

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

        phrases = autoComplete.getPhrase("42");

//        autoComplete.releaseConnection();

        System.out.println(phrases);
    }

    @Test
//  @Ignore
    public void testGetPhraseLoop() {
        AutoCompleteByTrie autoComplete = new AutoCompleteByTrie(jedisHelper);
        List<String> phrases = null;
        for (int i = 0; i < 100000; i++) {
            phrases = autoComplete.getPhrase("a");
        }
        assertNotNull(phrases);

        System.out.println(phrases);
    }
}
