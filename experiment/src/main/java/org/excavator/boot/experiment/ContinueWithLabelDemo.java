package org.excavator.boot.experiment;

public class ContinueWithLabelDemo {
    public static void main(String[] args) {
        var searchMe = "Look for a substring in me";
        var substring = "sub";
        var foundIt = false;

        var max = searchMe.length() - substring.length();

    test:
    for (int i = 0; i < max; i++) {
        var n = substring.length();
        var j = i;
        var k = 0;
        while(n -- != 0) {
            if(searchMe.charAt(j++) != substring.charAt(k++)) {
                continue test;
            }
        }
        foundIt = true;
        break test;
    }
    System.out.println(foundIt ? "Found it" : "Didn't find it");
    }
}
