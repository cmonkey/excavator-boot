package org.excavator.boot.experiment.stream;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class ExceptionsAndStreams {

    public void convertingCheckedIntoRuntimeExceptions(){
        var format = new SimpleDateFormat("yyyy-MM-dd") ;
        var dateList = asList("2020-10-11", "2020-nov-12", "2020-12-01");
        var dates = dateList.stream().map(s -> {
            try{
                return format.parse(s);
            }catch(ParseException e){
                throw new RuntimeException(e);
            }
        }).collect(toList());
    }
}
