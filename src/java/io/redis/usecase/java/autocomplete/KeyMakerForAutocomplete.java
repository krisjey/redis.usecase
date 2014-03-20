package io.redis.usecase.java.autocomplete;

import redis.clients.util.MurmurHash;
import io.redis.usecase.java.KeyMaker;

public class KeyMakerForAutocomplete implements KeyMaker {
	private static final int SEED_MURMURHASH = 0x1234ABCD;

	@Override
	public String getKey(String phrase) {
		return Long.toString(MurmurHash.hash64A(phrase.getBytes(), SEED_MURMURHASH));
	}
}
