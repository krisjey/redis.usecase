package net.sf.redisbook.autocomplete;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class KoreanSoundExtractorTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSoundExtractedString() {
        KoreanSoundExtractor koreanSoundExtractor = new KoreanSoundExtractor();

        String source = "위대한자 Tennis.";
        String expect = "ㅇㅟㄷㅐㅎㅏㄴㅈㅏ Tennis.";
        assertEquals(expect, koreanSoundExtractor.getSoundExtractedString(source));

        source = "찦차를 타고 온 펲시맨과 쑛다리 똠방각하";
        expect = "ㅉㅣㅍㅊㅏㄹㅡㄹ ㅌㅏㄱㅗ ㅇㅗㄴ ㅍㅔㅍㅅㅣㅁㅐㄴㄱㅘ ㅆㅛㅅㄷㅏㄹㅣ ㄸㅗㅁㅂㅏㅇㄱㅏㄱㅎㅏ";
        assertEquals(expect, koreanSoundExtractor.getSoundExtractedString(source));

        source = "ABC... .ABC";
        expect = "ABC... .ABC";
        assertEquals(expect, koreanSoundExtractor.getSoundExtractedString(source));
    }
}
