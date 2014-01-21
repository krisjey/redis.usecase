package io.redis.usecase.java.autocomplete;

import io.redis.usecase.java.JedisHelper;
import io.redis.usecase.java.autocomplete.AutoCompleteBuilderByTrie;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AutoCompleteByTriePutTest {
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
    public void testAddPhrase() {
        int score = 50;
        AutoCompleteBuilderByTrie autoCompleteBuilder = new AutoCompleteBuilderByTrie(jedisHelper);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("./document/samplebook.trie.txt"));
            if (reader != null) {
                while (true) {
                    String temp = reader.readLine();

                    if (temp == null) {
                        break;
                    }
                    autoCompleteBuilder.addPhrase(temp, score);
                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
