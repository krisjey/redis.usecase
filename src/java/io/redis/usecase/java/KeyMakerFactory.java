package io.redis.usecase.java;

import io.redis.usecase.java.autocomplete.KeyMakerForAutocomplete;

public class KeyMakerFactory {

	// FIXME remove.
	// public static KeyMaker makePhraseId(String phrase) {
	// }

	public static KeyMaker getKeyMakerForAutocomplete() {
		return new KeyMakerForAutocomplete();
	}

	public static KeyMaker getKeyMakerForLoginPV() {
		return null;
	}
}
