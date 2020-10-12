package com.zyz.usualcode.dateTime;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.YEARS;

/**
 * javax.time.ZoneId 处理时区
 * 所有的日期和时间都是不可变和线程安全的，而以前的 Date 和 SimpleDateFormat 不是
 *
 *
 *
 * @author 张远卓
 * @date 2020/10/12 17:42
 */
public class CommonUse {

    public static void main(String[] args) {
        // 当天
        LocalDate today = LocalDate.now();
        System.out.println("Today's Local date : " + today);

        // 年月日
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        System.out.printf("Year : %d Month : %d day : %d \t %n", year, month, day);

        // 年月日创建日期
        LocalDate dateOfBirth = LocalDate.of(2010, 1, 14);
        System.out.println("Your Date of birth is : " + dateOfBirth);

        // 年月日对比
        LocalDate date1 = LocalDate.of(2014, 1, 14);
        if (!date1.equals(today)) {
            System.out.printf("Today %s and date1 %s aren't same date %n", today, date1);
        }

        // 月日对比
        MonthDay birthday = MonthDay.of(dateOfBirth.getMonth(), dateOfBirth.getDayOfMonth());
        MonthDay currentMonthDay = MonthDay.from(today);
        if (currentMonthDay.equals(birthday)) {
            System.out.println("Many Many happy returns of the day !!");
        } else {
            System.out.println("Sorry, today is not your birthday");
        }
        System.out.println(currentMonthDay.isBefore(birthday) ? "生日还没到" : "生日已经过了");

        // 当前时间 HH:mm:ss.SSS
        LocalTime time = LocalTime.now();
        System.out.println("local time now : " + time);

        // 两小时后
        LocalTime newTime = time.plusHours(2); // adding two hours
        System.out.println("Time after 2 hours : " + newTime);

        // 一周后
        LocalDate nextWeek = today.plus(1, ChronoUnit.WEEKS);
        System.out.println("Today is : " + today);
        System.out.println("Date after 1 week : " + nextWeek);

        // 一年后
        LocalDate previousYear = today.minus(1, YEARS);
        System.out.println("Date before 1 year : " + previousYear);
        LocalDate nextYear = today.plus(1, YEARS);
        System.out.println("Date after 1 year : " + nextYear);

        // 时区
        // Returns the current time based on your system clock and set to UTC.
        Clock clock = Clock.systemUTC();
        System.out.println("Clock : " + clock);
        // Returns time based on system clock zone
        Clock defaultClock = Clock.systemDefaultZone();
        System.out.println("Clock : " + defaultClock);

        // 日期比较
        LocalDate tomorrow = LocalDate.of(2014, 1, 15);
        if (tomorrow.isAfter(today)) {
            System.out.println("Tomorrow comes after today");
        }
        LocalDate yesterday = today.minus(1, DAYS);
        if (yesterday.isBefore(today)) {
            System.out.println("Yesterday is day before today");
        }

        // 时区
        // Date and time with timezone in Java 8
        ZoneId america = ZoneId.of("America/New_York");
        ZoneId china = ZoneId.of("Asia/Shanghai");
        LocalDateTime localDateAndTime = LocalDateTime.now();
        ZonedDateTime dateAndTimeInNewYork = ZonedDateTime.of(localDateAndTime, america);
        System.out.println("Current date and time in a particular timezone : " + dateAndTimeInNewYork);

        // 一个月的天数
        YearMonth currentYearMonth = YearMonth.now();
        System.out.printf("Days in month year %s: %d%n", currentYearMonth, currentYearMonth.lengthOfMonth());
        YearMonth creditCardExpiry = YearMonth.of(2018, Month.FEBRUARY);
        System.out.printf("Your credit card expires on %s %n", creditCardExpiry);

        // 闰年
        if (today.isLeapYear()) {
            System.out.println("This year is Leap year");
        } else {
            System.out.println("2014 is not a Leap year");
        }

        // 时间间隔，以月为单位
        LocalDate java8Release = LocalDate.of(2014, Month.MARCH, 13);
        Period periodToNextJavaRelease = Period.between(today, java8Release);
        System.out.println("Months left between today and Java 8 release : " + periodToNextJavaRelease.getMonths());

        // 时区
        LocalDateTime datetime = LocalDateTime.of(2014, Month.JANUARY, 14, 19, 30);
        ZoneOffset offset = ZoneOffset.of("+05:30");
        OffsetDateTime date = OffsetDateTime.of(datetime, offset);
        System.out.println("Date and Time with timezone offset in Java : " + date);

        // 时间戳
        Instant timestamp = Instant.now();
        System.out.println("What is value of this instant " + timestamp.toEpochMilli());
        // 带时区的时间戳，与上一致
        System.out.println("What is value of China instant " + Instant.now().atZone(china).toEpochSecond());

        // 日期格式化，解析字符串
        String dayAfterTomorrow = "20140116";
        LocalDate formatted = LocalDate.parse(dayAfterTomorrow, DateTimeFormatter.BASIC_ISO_DATE);
        System.out.printf("Date generated from String %s is %s %n", dayAfterTomorrow, formatted);

        // 日期格式化，解析字符串
        String goodFriday = "08 18 2014";
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM dd yyyy");
            LocalDate holiday = LocalDate.parse(goodFriday, formatter);
            System.out.printf("Successfully parsed String %s, date is %s%n", goodFriday, holiday);
        } catch (DateTimeParseException ex) {
            System.out.printf("%s is not parsable!%n", goodFriday);
            ex.printStackTrace();
        }

        // 日期格式化，格式化输出
        LocalDateTime arrivalDate = LocalDateTime.now();
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
            String landing = arrivalDate.format(format);
            System.out.printf("Arriving at : %s %n", landing);
        } catch (DateTimeException ex) {
            System.out.printf("%s can't be formatted!%n", arrivalDate);
            ex.printStackTrace();
        }

    }
}
