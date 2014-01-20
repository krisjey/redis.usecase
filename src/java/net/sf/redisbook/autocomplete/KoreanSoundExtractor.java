package net.sf.redisbook.autocomplete;

/**
 * This class convert a korean word to untie a korean.
 * ex) 강남 ==> ㄱㅏㅇㄴㅏㅁ
 * @author kris
 */
public class KoreanSoundExtractor {
    /**
     * first sound 19 chars.
     */
    private static final char[] firstSounds = {
            'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ',
            'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    };

    /**
     * middle sound 21 chars.
     */
    private static final char[] middleSounds = {
            'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ',
            'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ',
            'ㅣ'
    };

    /**
     * space and last sound 27 chars.
     */
    private static final char[] lastSounds = {
            ' ', 'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ', 'ㄺ', 'ㄻ',
            'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ',
            'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    };

    /**
     * Extract three sound from one korean char.
     * If last sound does not exist then return two char array.
     * @param single korean char
     * @return char array of three sound char
     */
    public char[] splitSound(char c) {
        int lastSound = (c - 0xAC00) % 28;

        if (lastSound == 0) {
            char soundChar[] = new char[2];
            soundChar[0] = firstSounds[((c - 0xAC00) / (21 * 28))]; //초성의 위치
            soundChar[1] = middleSounds[(((c - 0xAC00) % (21 * 28)) / 28)]; //중성의 위치
            return soundChar;
        }
        else {
            char soundChar[] = new char[3];
            soundChar[0] = firstSounds[((c - 0xAC00) / (21 * 28))]; //초성의 위치
            soundChar[1] = middleSounds[(((c - 0xAC00) % (21 * 28)) / 28)]; //중성의 위치
            soundChar[2] = lastSounds[((c - 0xAC00) % (28))]; //종성의 위치
            return soundChar;
        }
    }

    /**
     * make a single korean char from three sound.
     * @param array of each sound location.
     * @return single korean char.
     */
    public char combine(int[] sub) {
        return (char) (0xAC00 + (sub[0] * 21 * 28) + (sub[1] * 28) + sub[2]);
    }

    /**
     * Extract three sound from phrase.
     * If there is not exist korean char then return same phrase.
     * @param phrase
     * @return converted phrase.
     */
    public String getSoundExtractedString(String phrase) {
        // TODO check before convert for performance
        StringBuilder builder = new StringBuilder();

        for (char character : phrase.toCharArray()) {
            if (character >= 0xAC00) {
                char[] soundString = splitSound(character);
                for (int i = 0; i < soundString.length; i++) {
                    builder.append(soundString[i]);
                }
            }
            else {
                builder.append(character);
            }
        }

        return builder.toString();
    }
}
