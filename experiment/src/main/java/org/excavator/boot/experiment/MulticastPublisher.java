package org.excavator.boot.experiment;

import java.io.IOException;
import java.net.*;

public class MulticastPublisher {
    private DatagramSocket socket;
    private InetAddress group;
    private byte[] buf;
    public void multicast(String multicastMessage) throws IOException {

        socket = new DatagramSocket();
        group = InetAddress.getByName("224.0.0.1");
        buf = multicastMessage.getBytes();
        var packet = new DatagramPacket(buf, buf.length, group, 4446);
        socket.send(packet);
        socket.close();
    }
}
