package org.excavator.boot.experiment;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastReceiver extends Thread{
    protected MulticastSocket socket = null;
    protected byte[] buf = new byte[256];

    public void run(){
        try {
            socket = new MulticastSocket(4446);
            var group = InetAddress.getByName("224.0.0.1");
            socket.joinGroup(group);
            while(true){
                var packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                var received = new String(packet.getData(), 0, packet.getLength());
                System.out.println(received);
                if("end".equals(received)){
                    break;
                }
            }
            socket.leaveGroup(group);
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
