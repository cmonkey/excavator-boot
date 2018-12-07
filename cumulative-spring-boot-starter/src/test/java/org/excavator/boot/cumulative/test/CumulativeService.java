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
package org.excavator.boot.cumulative.test;

import org.excavator.boot.cumulative.service.Cumulative;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CumulativeService {
    private final static Logger logger = LoggerFactory.getLogger(CumulativeService.class);

    @Resource
    private Cumulative          cumulative;

    public String cumulativeByDay(String key, String dimensionKey, String value) {
        try {
            cumulative.countByDay(key, new Cumulative.Dimension(dimensionKey, value));
        } catch (IllegalAccessException e) {
            logger.error("cumulative Exception = {}", e);
        }
        return cumulative.queryByDay(key, dimensionKey);
    }

    public String cumulativeByMonth(String key, String dimensionKey, String value) {
        try {
            cumulative.countByMonth(key, new Cumulative.Dimension(dimensionKey, value));
        } catch (IllegalAccessException e) {
            logger.error("cumulative Exception = {}", e);
        }
        return cumulative.queryByMonth(key, dimensionKey);
    }

    public String cumulativeByYear(String key, String dimensionKey, String value) {
        try {
            cumulative.countByYear(key, new Cumulative.Dimension(dimensionKey, value));
        } catch (IllegalAccessException e) {
            logger.error("cumulative Exception = {}", e);
        }
        return cumulative.queryByYear(key, dimensionKey);
    }

    public void cumulativeByDayAndMonth(String key, String dimensionKey, String value) {
        try {
            cumulative.countByDayAndMonth(key, new Cumulative.Dimension(dimensionKey, value));
        } catch (IllegalAccessException e) {
            logger.error("cumulative Exception = {}", e);
        }
    }

    public String queryByDay(String key, String dimensionKey) {
        return cumulative.queryByDay(key, dimensionKey);
    }

    public String queryByMonth(String key, String dimensionKey) {
        return cumulative.queryByMonth(key, dimensionKey);
    }

    public String queryByYear(String key, String dimensionKey) {
        return cumulative.queryByYear(key, dimensionKey);
    }
}
