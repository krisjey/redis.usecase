package net.sf.redisbook.autocomplete;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

import net.sf.redisbook.JedisHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AutoCompleteTest {
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
        AutoComplete autoComplete = new AutoComplete(jedisHelper);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("./document/samplebook.trie.txt"));
            if (reader != null) {
                while (true) {
                    String temp = reader.readLine();

                    if (temp == null) {
                        break;
                    }
                    autoComplete.addPhrase(temp, score);
                }
            }
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testGetPhrase() {
        AutoComplete autoComplete = new AutoComplete(jedisHelper);
        Set<String> phrases = null;
        for (int i = 0; i < 10000; i++) {
            phrases = autoComplete.getPhrase("4");
        }
        assertNotNull(phrases);
        System.out.println(phrases);
    }
}
