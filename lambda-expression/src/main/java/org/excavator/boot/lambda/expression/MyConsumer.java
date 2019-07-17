package org.excavator.boot.lambda.expression;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
public class MyConsumer implements Consumer<String> {

    @Override
    public void accept(String s) {
        log.info("This {} is consumed", s);
    }
}
