package com.nymbus.core.utils;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

public class DateTime {

    public static String getLocalDateTimeByPattern(String pattern) {
        return DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH).format(LocalDateTime.now());
    }

    public static String getCurrentDayOfWeek() {
        return LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }

    public static String getDayOfWeek(String date) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("MMM d, yyyy"));
        return localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }

    public static int getDayOfMonth(String date) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("MMM d, yyyy"));
        return localDate.getDayOfMonth();
    }

    public static String getPreviousMonthDay(int i) {
        return Integer.toString(MonthDay.now().getDayOfMonth() - i);
    }

    public static String getCurrentMonthDay() {
        return Integer.toString(MonthDay.now().getDayOfMonth());
    }

    public static String getNextMonthDay() {
        return Integer.toString(MonthDay.now().getDayOfMonth() + 1);
    }

    public static String getNextMonthDay(int i) {
        return Integer.toString(MonthDay.now().getDayOfMonth() + i);
    }

    public static String getCurrentMonth(String pattern) {
        return YearMonth.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String getNextMonth() {
        return YearMonth.now().plusMonths(1).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getCurrentYear(String pattern) {
        return Year.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String getCurrentHours() {
        return Integer.toString(LocalTime.now().getHour());
    }

    public static String getCurrentMinutes() {
        return LocalTime.now().format(DateTimeFormatter.ofPattern("mm"));
    }

    public static String parseTime(String time) {
        return LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm")).format(DateTimeFormatter.ofPattern("h:mma"));
    }

    public static String getLocalDate() {
        return DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH).format(LocalDate.now());
    }

    public static String parseDate(String date) {
        return DateTimeFormatter.ofPattern("MMMM d, yyyy").format(LocalDate.parse(date));
    }

    public static String parseDate(String date, String monthFormat) {
        return DateTimeFormatter.ofPattern(String.format("%s d, yyyy", monthFormat), Locale.ENGLISH).format(LocalDate.parse(date));
    }

    public static String parseDate(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.ENGLISH);
        return format.format(date);
    }

    public static int getDaysInMonth(int monthNumber) {
        YearMonth yearMonthObject = YearMonth.of(Year.now().getValue(), monthNumber);
        return yearMonthObject.lengthOfMonth();
    }

    public static List<Integer> getDaysInCurrentMonthByDayOfWeek(String dayOfWeek) {
        YearMonth yearMonthObject = YearMonth.of(Year.now().getValue(), YearMonth.now().getMonthValue());
        int daysInMonth = yearMonthObject.lengthOfMonth();

        List<Integer> daysList = new ArrayList<>();
        for (int i = 1; i <= daysInMonth; i++) {
            String day = LocalDate.of(Year.now().getValue(), YearMonth.now().getMonthValue(), i).getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
            if (day.equals(dayOfWeek)) {
                daysList.add(i);
            }
        }

        return daysList;
    }

    public static String plusMonthsToCurrentDateWithLastDayOfMonth(int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, months);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
        String futureDate = simpleDateFormat.format(calendar.getTime());

        return futureDate;
    }

    public static String plusMonthsToCurrentDateWithLastDayOfMonth(int months, String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, months);
        calendar.add(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - calendar.get(Calendar.DAY_OF_MONTH));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.ENGLISH);
        String futureDate = simpleDateFormat.format(calendar.getTime());

        return futureDate;
    }
}
