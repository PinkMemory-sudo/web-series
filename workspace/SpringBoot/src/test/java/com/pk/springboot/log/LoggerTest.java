package com.pk.springboot.log;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.logging.Logger;

@SpringBootTest
public class LoggerTest {

    @Test
    public void loggerTest(){
        Logger logger = Logger.getLogger("logger1");
        logger.info("info");
        logger.warning("warn");
    }
}
