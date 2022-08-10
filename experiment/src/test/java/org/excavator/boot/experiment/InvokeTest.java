package org.excavator.boot.experiment;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class InvokeTest {

    @SneakyThrows
    @Test
    @DisplayName("test invoke")
    public void testInvoke(){
        var names = new ArrayList<String>();
        var add = List.class.getMethod("add", Object.class);
        add.invoke(names, "John");
        add.invoke(names, "Anton");
        add.invoke(names, "Heinz");
        System.out.println("names = " + names);
        add.invoke(names, null);
        System.out.println("names = " + names);
        add.invoke(names, 42);
        System.out.println("names = " + names);
        for (String name: names)System.out.println(name);
    }
}
