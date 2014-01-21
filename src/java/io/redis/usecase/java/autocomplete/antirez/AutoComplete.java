package io.redis.usecase.java.autocomplete.antirez;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

/**
 * A Redis based autocomplete utility class.
 *
 * @author xp
 */
public class AutoComplete {

    private final Jedis redis;

    private final String redisKey;

    AutoComplete(final Jedis redis, final String redisKey, final URL dictionaryUrl, final boolean populate,
        final boolean clear) throws IOException {
        this.redis = redis;
        this.redisKey = redisKey;

        if (clear) {
            // Truncate existing Dictionary
            clearDictionary();
        }
        if (populate) {
            // Load the entries from dictionaryUrl
            loadDictionary(dictionaryUrl);
        }
    }

    public List<String> complete(final String prefix, final int count) {
        // 1. Find the position of the prefix in the sorted set in Redis
        if (null == prefix) {
            return Collections.emptyList();
        }
        int prefixLength = prefix.length();
        Long start = redis.zrank(redisKey, prefix);
        if (start < 0 || prefixLength == 0) {
            return Collections.emptyList();
        }
        List<String> results = new ArrayList<String>();
        int found = 0, rangeLength = 50, maxNeeded = count;
        while (found < maxNeeded) {
            Set<String> rangeResults = redis.zrange(redisKey, start, start + rangeLength - 1);
            start += rangeLength;
            if (rangeResults.isEmpty()) {
                break;
            }
            for (final String entry : rangeResults) {
                int minLength = Math.min(entry.length(), prefixLength);
                if (!entry.substring(0, minLength).equalsIgnoreCase(prefix.substring(0, minLength))) {
                    maxNeeded = results.size();
                    break;
                }
                if (entry.endsWith("*") && results.size() < maxNeeded) {
                    results.add(entry.substring(0, entry.length() - 2));
                }
            }
        }
        return results;
    }

    private void clearDictionary() {
        redis.del(redisKey);
    }

    /**
     * Load the entries from dictionaryUrl. Add all the words along with all the
     * possible prefixes of any word to Redis Set
     *
     * @param dictionaryUrl {@link URL} where the dictionary is present
     * @throws IOException In case of exception in reading dictionary
     * @throws RedisException In case of exception in populating the redis with
     *             the dictionary entries
     */
    private void loadDictionary(final URL dictionaryUrl) throws IOException {
//        InputStreamReader inputStreamReader = new InputStreamReader(dictionaryUrl.openStream());
        BufferedReader reader = new BufferedReader(new FileReader("./document/samplebook.trie.txt"));
        String word;
        while ((word = reader.readLine()) != null) {
            word = word.trim();
            // Add the word if the word does not start with #
            if (!word.isEmpty() && !word.startsWith("#")) {
                addWord(word);
            }
        }
        reader.close();
    }

    private void addWord(final String word) {
        // Add all the possible prefixes of the given word and also the given
        // word with a * suffix.
        redis.zadd(redisKey, 0, word + "*");
        for (int index = 1, total = word.length(); index < total; index++) {
            redis.zadd(redisKey, 0, word.substring(0, index));
        }
    }

    public static class Builder {
        private final Jedis redis;
        private final String redisKey;
        private final URL dictionaryUrl;
        private boolean clear = true;
        private boolean populate = true;

        public Builder(final Jedis redis, final String redisKey, final URL dictionaryUrl) {
            this.redis = redis;
            this.redisKey = redisKey;
            this.dictionaryUrl = dictionaryUrl;
        }

        public Builder clear(final boolean clear) {
            this.clear = clear;
            this.populate = true;
            return this;
        }

        public Builder populate(final boolean populate) {
            this.populate = populate;
            return this;
        }

        public AutoComplete build() throws IOException {
            return new AutoComplete(redis, redisKey, dictionaryUrl, populate, clear);
        }
    }
}
