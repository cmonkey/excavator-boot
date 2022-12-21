package org.excavator.boot.experiment.httpserver;

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
