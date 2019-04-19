package org.excavator.boot.logging.test;

import ch.qos.logback.classic.LoggerContext;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.LoggerFactory;

public class LogBackInitializerExtension implements BeforeAllCallback {
    @Override
    public void beforeAll(ExtensionContext context) throws Exception {

        pauseTillLogBackReady();
    }

    private void pauseTillLogBackReady() {
        do {

        }while (!isLogBackReady())
    }

    private boolean isLogBackReady() {
        try{
            LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        }catch(ClassCastException e){
            return false;
        }

        return true;
    }
}
