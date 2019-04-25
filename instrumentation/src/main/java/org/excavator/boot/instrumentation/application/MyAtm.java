package org.excavator.boot.instrumentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class MyAtm {
    private final static Logger logger = LoggerFactory.getLogger(MyAtm.class);

    public static void withdrawMoney(int amount){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("[Application] successful withdrawal of [{}] units", amount);
    }
}
