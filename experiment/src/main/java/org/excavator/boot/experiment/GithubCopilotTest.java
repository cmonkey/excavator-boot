package org.excavator.boot.experiment;

public class GithubCopilotTest {

    public static void main(String[] args) {

        var example = new Example("name");
        System.out.println(example.getName());
    }
}

record Example(String name) {
    public String getName() {
        return name;
    }
}
