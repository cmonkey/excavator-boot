package org.excavator.boot.experiment.stream;

import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class ExceptionsAndStreams {

    public void convertingCheckedIntoRuntimeExceptions(){
        var format = new SimpleDateFormat("yyyy-MM-dd") ;
        var dateList = asList("2020-10-11", "2020-nov-12", "2020-12-01");
        var dates = dateList.stream().map(s -> uglyParse(format, s)).collect(toList());
    }
    @SneakyThrows
    private static Date uglyParse(SimpleDateFormat format, String s){
        return format.parse(s);
    }
}
