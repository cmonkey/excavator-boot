package org.excavator.boot.experiment.httpserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class MyHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        var headers = exchange.getResponseHeaders();
        var remoteAddress = exchange.getRemoteAddress();
        System.out.println(headers);
        System.out.println(remoteAddress);
    }
}
