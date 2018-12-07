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
package org.excavator.boot.cumulative.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

public class DateTimeUtils {
    public static final DateTimeFormatter DEFAULT_DATETIME_FORMATTER       = DateTimeFormatter
                                                                               .ofPattern("yyyyMMddHHmmss");
    public static final DateTimeFormatter DEFAULT_SHORT_DATETIME_FORMATTER = DateTimeFormatter
                                                                               .ofPattern("yyMMddHHmmss");
    public static final DateTimeFormatter DEFAULT_DATE_FORMATTER           = DateTimeFormatter
                                                                               .ofPattern("yyyyMMdd");
    public static final DateTimeFormatter DEFAULT_SHORT_DATE_FORMATTER     = DateTimeFormatter
                                                                               .ofPattern("yyMMdd");
    public static final DateTimeFormatter DEFAULT_MONTH_FORMATTER          = DateTimeFormatter
                                                                               .ofPattern("yyyyMM");
    public static final DateTimeFormatter DEFAULT_TIME_FORMATTER           = DateTimeFormatter
                                                                               .ofPattern("HHmmss");

    public static final DateTimeFormatter DEFAULT_YEAR_FORMATTER           = DateTimeFormatter
                                                                               .ofPattern("yyyy");

    public DateTimeUtils() {
    }

    public static LocalDate getCurrentLocalDate() {
        return LocalDate.now();
    }

    public static LocalTime getCurrentLocalTime() {
        return LocalTime.now();
    }

    public static LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now();
    }

    public static String getCurrentDateStr() {
        return LocalDate.now().format(DEFAULT_DATE_FORMATTER);
    }

    public static String getCurrentShortDateStr() {
        return LocalDate.now().format(DEFAULT_SHORT_DATE_FORMATTER);
    }

    public static String getCurrentMonthStr() {
        return LocalDate.now().format(DEFAULT_MONTH_FORMATTER);
    }

    public static String getCurrentDateTimeStr() {
        return LocalDateTime.now().format(DEFAULT_DATETIME_FORMATTER);
    }

    public static String getCurrentShortDateTimeStr() {
        return LocalDateTime.now().format(DEFAULT_SHORT_DATETIME_FORMATTER);
    }

    public static String getCurrentTimeStr() {
        return LocalTime.now().format(DEFAULT_TIME_FORMATTER);
    }

    public static String getCurrentDateStr(String pattern) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String getCurrentDateTimeStr(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String getCurrentTimeStr(String pattern) {
        return LocalTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDate parseLocalDate(String dateStr, String pattern) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDateTime parseLocalDateTime(String dateTimeStr, String pattern) {
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalTime parseLocalTime(String timeStr, String pattern) {
        return LocalTime.parse(timeStr, DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatLocalDate(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatLocalDateTime(LocalDateTime datetime, String pattern) {
        return datetime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatLocalTime(LocalTime time, String pattern) {
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDate parseLocalDate(String dateStr) {
        return LocalDate.parse(dateStr, DEFAULT_DATE_FORMATTER);
    }

    public static LocalDateTime parseLocalDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DEFAULT_DATETIME_FORMATTER);
    }

    public static LocalTime parseLocalTime(String timeStr) {
        return LocalTime.parse(timeStr, DEFAULT_TIME_FORMATTER);
    }

    public static String formatLocalDate(LocalDate date) {
        return date.format(DEFAULT_DATE_FORMATTER);
    }

    public static String formatLocalDateTime(LocalDateTime datetime) {
        return datetime.format(DEFAULT_DATETIME_FORMATTER);
    }

    public static String formatLocalTime(LocalTime time) {
        return time.format(DEFAULT_TIME_FORMATTER);
    }

    public static int periodDays(LocalDate startDateInclusive, LocalDate endDateExclusive) {
        return Period.between(startDateInclusive, endDateExclusive).getDays();
    }

    public static long durationHours(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive).toHours();
    }

    public static long durationMinutes(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive).toMinutes();
    }

    public static long durationMillis(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive).toMillis();
    }

    public static boolean isToday(LocalDate date) {
        return getCurrentLocalDate().equals(date);
    }

    public static Long toEpochMilli(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static String getCurrentYearStr() {
        return LocalDate.now().format(DEFAULT_YEAR_FORMATTER);
    }
}