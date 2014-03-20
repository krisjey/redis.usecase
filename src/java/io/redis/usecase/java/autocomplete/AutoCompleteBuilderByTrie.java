package io.redis.usecase.java.autocomplete;

import io.redis.usecase.java.JedisHelper;
import io.redis.usecase.java.KeyMaker;
import io.redis.usecase.java.KeyMakerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

public class AutoCompleteBuilderByTrie {
	private final Jedis jedis;

	public AutoCompleteBuilderByTrie(JedisHelper jedisHelper) {
		jedis = jedisHelper.getConnection();
	}

	/**
	 * Add a phrase to autocomplete dic.
	 * 
	 * @param phrase
	 * @param score
	 */
	public void addPhrase(String phrase, int score) {
		Pipeline pipeline = jedis.pipelined();

		KeyMaker keyMaker = KeyMakerFactory.getKeyMakerForAutocomplete();

		KoreanSoundExtractor koreanSoundExtractor = new KoreanSoundExtractor();
		String result = koreanSoundExtractor.getSoundExtractedString(phrase);

		String key = keyMaker.getKey(phrase);
		StringBuilder builder = new StringBuilder(result.length());
		for (char element : result.toCharArray()) {
			builder.append(element);
			pipeline.zadd(builder.toString(), score, key);
		}

		// set phrase to redis by string data type
		pipeline.set(key, phrase);

		pipeline.sync();
	}
}
