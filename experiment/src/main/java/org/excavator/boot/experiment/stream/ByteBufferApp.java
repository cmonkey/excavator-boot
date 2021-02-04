package org.excavator.boot.experiment.stream;

public class ByteBufferApp {

    public static void main (String [] args) {
        var buffer = ByteBuffer.allocate(6);
        printCurrentState("After allocation", buffer);

        buffer.put(new byte[]{10, 20, 30, 40});
        printCurrentState("After put", buffer);

        buffer.flip();
        printCurrentState("After flip", buffer);

        buffer.get();
        printCurrentState("After get", buffer);

        buffer.rewind();
        printCurrentState("After rewind", buffer);

        while(buffer.hasRemaining()){
            System.out.printf("[%d-%d] ", buffer.position(), buffer.get());
        }

        System.out.println();

        buffer.clear();
        printCurrentState("After clear", buffer);
    }
}
