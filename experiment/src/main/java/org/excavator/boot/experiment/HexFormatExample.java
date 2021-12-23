package org.excavator.boot.experiment;

import java.util.Arrays;
import java.util.HexFormat;

public class HexFormatExample {

    public static void main(String[] args) {
        var format = HexFormat.of();
        var input = new byte[]{127, 0, -50, 105};
        var hex = format.formatHex(input);
        System.out.println(hex);

        var output = format.parseHex(hex);
        assert Arrays.compare(input, output) == 0;
    }
}
