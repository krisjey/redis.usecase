package io.redis.usecase.java;

import static org.junit.Assert.*;
import io.redis.usecase.java.JedisHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PVTest {
    static JedisHelper helper;
    private static final int VISIT_COUNT = 1000;
    private static final int TOTAL_USER = 10000000;
    private static final String TEST_DATE = "19500101";
    private static final int TEST_TERM = 10;
    private static final String[] dateList = new String[TEST_TERM];

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        helper = JedisHelper.getInstance();
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        calendar.add(Calendar.DAY_OF_MONTH, -TEST_TERM);
        for (int i = TEST_TERM; i > 0; i--) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            dateList[i - 1] = simpleDateFormat.format(calendar.getTime());
        }
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        helper.destoryPool();
    }

    @Test
    public void test() {
        // 10일간의 PV 데이터 만들기. 방문수는 1만정도로.
        for (int i = 0; i < TEST_TERM; i++) {

        }

        // PV 데이터 조회하기.

    }

}
