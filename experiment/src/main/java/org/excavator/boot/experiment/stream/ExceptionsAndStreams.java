/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.excavator.boot.experiment.stream;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class ExceptionsAndStreams {

    public List<Date> convertingCheckedIntoRuntimeExceptions(){
        var format = new SimpleDateFormat("yyyy-MM-dd") ;
        var dateList = asList("2020-10-11", "2020-nov-12", "2020-12-01");
        //ThrowingFunction<String, Date> p = s -> format.parse(s);
        ThrowingFunction<String, Date> p = format::parse;
        var f = wrapAsRuntime(p);
        var dates = dateList.stream().map(f).collect(toList());

        return dates;
    }

    private static Date uglyParse(SimpleDateFormat format, String s) {
        return format.parse(s);
    }

    private static <T, R> Function<T, R> wrapAsRuntime(ThrowingFunction<T, R> p){
        return t -> {
            try{
                return p.apply(t);
            }catch(Exception e){
                throw new RuntimeException(e);
            }
        };
    }
}
