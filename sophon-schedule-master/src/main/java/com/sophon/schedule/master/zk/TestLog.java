package com.sophon.schedule.master.zk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/11/14 10:35
 */
public class TestLog {

    public static Logger logger = LoggerFactory.getLogger(TestLog.class);


    public void test() {

        for (int i = 0; i < 100; i++) {
            logger.info("test-log" + i);
        }
    }


    public static void main(String[] args) {
        TestLog testLog = new TestLog();
        testLog.test();
    }
}
