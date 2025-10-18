package org.excavator.boot.experiment;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class MulticastTest {
    public static void main(String[] args) throws IOException {
        var multicastTest = new MulticastTest();
        multicastTest.listAllBroadcastAddresses().forEach(System.out::println);
        var receiver = new MulticastReceiver();
        receiver.start();

        var publisher = new MulticastPublisher();
        IntStream.range(0, 10).forEach(i -> {
            try {
                publisher.multicast("hello"+i);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        publisher.multicast("end");
    }

    List<InetAddress> listAllBroadcastAddresses() throws SocketException {
        List<InetAddress> broadcastList = new ArrayList<>();
        Enumeration<NetworkInterface> interfaces
                = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();

            if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                continue;
            }

            networkInterface.getInterfaceAddresses().stream()
                    .map(a -> a.getBroadcast())
                    .filter(Objects::nonNull)
                    .forEach(broadcastList::add);
        }
        return broadcastList;
    }
}
