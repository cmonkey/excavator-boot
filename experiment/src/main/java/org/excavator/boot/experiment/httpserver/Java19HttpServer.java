package org.excavator.boot.experiment.httpserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Java19HttpServer {

    public static void main(String[] args) throws IOException {

        var httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
        httpServer.createContext("/persons", new MyHandler());
        httpServer.setExecutor(Executors.newVirtualThreadPerTaskExecutor());

        httpServer.start();
    }
}
class MyHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        var headers = exchange.getResponseHeaders();
        var remoteAddress = exchange.getRemoteAddress();
        System.out.println(headers);
        System.out.println(remoteAddress);
    }
}
