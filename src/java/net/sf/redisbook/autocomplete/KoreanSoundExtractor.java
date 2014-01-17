package net.sf.redisbook.autocomplete;

public class KoreanSoundExtractor {
    /**
     * 초성 19자
     */
    private static final char[] firstSounds = {
            'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ',
            'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    };

    /**
     * 중성 21자
     */
    private static final char[] middleSounds = {
            'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ',
            'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ',
            'ㅣ'
    };

    /**
     * 공백 문자 + 종성 27자.
     */
    private static final char[] lastSounds = {
            ' ', 'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ', 'ㄺ', 'ㄻ',
            'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ',
            'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    };

    /**
     * 문자 하나가 한글인지 검사
     *
     * @param c 검사 하고자 하는 문자
     * @return 한글 여부에 따라 'true' or 'false'
     */
    private boolean isKorean(char c) {
        if (c < 0xAC00 || c > 0xD7A3)
            return false;
        return true;
    }

    /**
     * 문자열이 한글인지 검사
     *
     * @param str 검사 하고자 하는 문자열
     * @return 한글 여부에 따라 'true' or 'false'
     */
    private boolean isKorean(String str) {
        if (str == null)
            return false;

        int len = str.length();
        if (len == 0)
            return false;

        for (int i = 0; i < len; i++) {
            if (!isKorean(str.charAt(i)))
                return false;
        }
        return true;
    }

    /**
     * 한글 문자의 마지막 문자의 종성 코드값을 추출
     *
     * @param c 추출 하고자 하는 문자
     * @return 존재하지 않으면 '0', 존재하면 코드값 (한글이 아닐때 '-1')
     */
    private int getLastElementCode(char c) {
        if (!isKorean(c))
            return -1;
        return (c - 0xAC00) % 28;
    }

    /**
     * 한글 문자열의 마지막 문자의 종성 코드값을 추출
     *
     * @param str 추출 하고자 하는 문자열
     * @return 존재하지 않으면 '0', 존재하면 코드값 (한글이 아닐때 '-1')
     */
    private int getLastElementCode(String str) {
        if (str == null)
            return -1;

        int len = str.length();
        if (len == 0)
            return -1;

        return getLastElementCode(str.charAt(len - 1));
    }

    /**
     * 마지막 한글 문자의 종성이 존제하는 검사
     *
     * @param c 검사 하고자 하는 문자
     * @return 존재하지 않으면 'false', 존재하면 'true'
     */
    private boolean hasLastElement(char c) {
        if (getLastElementCode(c) > 0)
            return true;
        return false;
    }

    /**
     * 한글 만자열의 마지막 문자의 종성이 존제하는 검사
     *
     * @param str 검사 하고자 하는 문자열
     * @return 존재하지 않으면 'false', 존재하면 'true'
     */
    private boolean hasLastElement(String str) {
        if (str == null)
            return false;

        int len = str.length();
        if (len == 0)
            return false;

        return hasLastElement(str.charAt(len - 1));
    }

    /**
     * 한글 문자의 초성을 추출
     *
     * @param c 첫번째 문자의 요소를 추출할 문자열
     * @return 한글 문자의 초성
     */
    private char getFirstElement(char c) {
        if (!isKorean(c))
            return c;
        return firstSounds[(c - 0xAC00) / (21 * 28)];
    }

    /**
     * 문자열의 첫번째 요소를 추출 (한글일 경우 초성을 추출)
     *
     * @param str 첫번째 문자의 요소를 추출할 문자열
     * @return 첫번째 요소 (한글일 경우 첫번째 문자의 자음)
     */
    private char getFirstElement(String str) {
        if (str == null)
            return '\u0000';

        int len = str.length();
        if (len == 0)
            return '\u0000';

        return getFirstElement(str.charAt(0));
    }

    /**
     * 한글 한 글자(char)를 받아 초성, 중성, 종성 글자로 반환한다.
     * 종성이 없으면 2개의 배열로 반환한다.
     * @param char : 한글 한 글자
     * @return char[] : 한글 초, 중, 종성글자.
     */
    public char[] splitSound(char c) {
        int lastSound = (c - 0xAC00) % 28;

        if (lastSound == 0) {
            char soundChar[] = new char[2];
            soundChar[0] = firstSounds[((c - 0xAC00) / (21 * 28))]; //초성의 위치
            soundChar[1] = middleSounds[(((c - 0xAC00) % (21 * 28)) / 28)]; //중성의 위치
            return soundChar;
        }
        else    {
            char soundChar[] = new char[3];
            soundChar[0] = firstSounds[((c - 0xAC00) / (21 * 28))]; //초성의 위치
            soundChar[1] = middleSounds[(((c - 0xAC00) % (21 * 28)) / 28)]; //중성의 위치
            soundChar[2] = lastSounds[((c - 0xAC00) % (28))]; //종성의 위치
            return soundChar;
        }
    }

    /**
     * 한글 한 글자를 구성 할 초성, 중성, 종성을 받아 조합 후 char[]로 반환 한다.
     * @param int[] : 한글 초, 중, 종성의 위치( ex:가 0,0,0 )
     * @return char[] : 한글 한 글자
     */
    public char[] combine(int[] sub) {
        char[] ch = new char[1];
        ch[0] = (char) (0xAC00 + (sub[0] * 21 * 28) + (sub[1] * 28) + sub[2]);
        return ch;
    }

    /**
     * 한글 초,중,종성 분리/조합 테스트 메소드
     */
    private void doSomething() {
        String str = "찦차를 타고 온 펲시맨과 쑛다리 똠방각하";
        System.out.println("============한글 분리============");
        System.out.println(getSoundExtractedString(str));

        System.out.println("rn============한글 조합============");
        System.out.println("0,0,0 : " + new String(combine(new int[] { 0, 0, 0 })));
        System.out.println("2,0,0 : " + new String(combine(new int[] { 2, 0, 0 })));
        System.out.println("3,0,0 : " + new String(combine(new int[] { 3, 0, 0 })));
        System.out.println("11,11,12 : " + new String(combine(new int[] { 11, 11, 10 })));
        System.out.println("10,11,12 : " + new String(combine(new int[] { 10, 11, 14 })));
    }

    public static void main(String[] args)   {
        KoreanSoundExtractor koreanSoundExtractor = new KoreanSoundExtractor();
        koreanSoundExtractor.doSomething();
    }

    public String getSoundExtractedString(String source) {
        StringBuilder builder = new StringBuilder();

        for (char character : source.toCharArray()) {
            if (character >= 0xAC00) {
                char[] soundString = splitSound(character);
                for (int i = 0; i < soundString.length; i++) {
                    builder.append(soundString[i]);
                }
            }
            else    {
                builder.append(character);
            }
        }

        return builder.toString();
    }
}
