package com.zyz.usualcode.dateTime;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoUnit.*;

/**
 * javax.time.ZoneId 处理时区
 * 所有的日期和时间都是不可变和线程安全的，而以前的 Date 和 SimpleDateFormat 不是
 *
 * @author 张远卓
 * @date 2020/10/12 17:42
 */
public class CommonUse {

    public static void main(String[] args) {
        // 获取当前日期时间
        // 2020-03-25T13:18:51.052775200Z
        System.out.println(Instant.now());
        // 2020-03-25T21:18:51.064769800
        System.out.println(LocalDateTime.now());
        // 2020-03-25
        System.out.println(LocalDate.now());
        // 21:18:51.064769800
        System.out.println(LocalTime.now());
        // 2020
        System.out.println(Year.now());
        // 2020-03
        System.out.println(YearMonth.now());
        // --03-25
        System.out.println(MonthDay.now());

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

        // 前一天零点时间戳
        long yesterdayStartTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).minusDays(1).toEpochSecond(ZoneOffset.of("+8"));
        System.out.println(yesterdayStartTime);

        // 时间戳转时间字符串
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(1602572427000L), china);
            String timeStr = format.format(localDateTime);
            System.out.println(timeStr);
        } catch (DateTimeException ex) {
            ex.printStackTrace();
        }

        // 时间字符串转时间戳
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime parse = LocalDateTime.parse("2020-10-13 15:00:27", format);
            long milli = LocalDateTime.from(parse).atZone(china).toInstant().toEpochMilli();
            System.out.println(milli);
        } catch (DateTimeException ex) {
            ex.printStackTrace();
        }

        // 获取指定日期时间
        // 2020-03-25T21:44:32
        LocalDateTime.of(2020, 3, 25, 21, 44, 32);
        // 2020-03-25
        LocalDate.of(2020, 3, 25);
        // 21:44:32
        LocalTime.of(21, 44, 32);
        // 2020
        Year.of(2020);
        // 2020-03
        YearMonth.of(2020, 3);
        // --03-25
        MonthDay.of(3, 25);

        // 指定日期 2019-02-09
        LocalDate localDate = LocalDate.of(2019, 2, 9);
        // 这个月的第一天 2019-02-01
        localDate.with(TemporalAdjusters.firstDayOfMonth());
        // 这个月的最后一天 2019-02-28
        localDate.with(TemporalAdjusters.lastDayOfMonth());
        // 下个月的第一天 2019-03-01
        localDate.with(TemporalAdjusters.firstDayOfNextMonth());
        // 这一年的第一天 2019-01-01
        localDate.with(TemporalAdjusters.firstDayOfYear());
        // 这一年的最后一天 2019-12-31
        localDate.with(TemporalAdjusters.lastDayOfYear());
        // 下一年的第一天 2020-01-01
        localDate.with(TemporalAdjusters.firstDayOfNextYear());
        // 这个月的第一个星期一 2019-02-04
        localDate.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
        // 这个月的最后一个星期一 2019-02-25
        localDate.with(TemporalAdjusters.lastInMonth(DayOfWeek.MONDAY));
        // 这个月的第二个星期一 2019-02-11
        localDate.with(TemporalAdjusters.dayOfWeekInMonth(2, DayOfWeek.MONDAY));
        // 这个月的倒数第二个星期一 2019-02-18
        localDate.with(TemporalAdjusters.dayOfWeekInMonth(-2, DayOfWeek.MONDAY));
        // 下一个星期六 2019-02-16
        localDate.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
        // 下一个星期六，可以是当天 2019-02-09
        localDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
        // 上一个星期六 2019-02-02
        localDate.with(TemporalAdjusters.previous(DayOfWeek.SATURDAY));
        // 上一个星期六，可以是当天 2019-02-09
        localDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SATURDAY));
        // 下个月的最后一天 2019-03-31
        localDate.with(temporal -> {
            Temporal nextMonths = temporal.plus(1, MONTHS);
            return nextMonths.with(DAY_OF_MONTH, nextMonths.range(DAY_OF_MONTH).getMaximum());
        });

        // 指定日期时间
        LocalDateTime localDateTime = LocalDateTime.of(2020, 3, 25, 21, 44, 32);
        // 这一天的开始时间
        localDateTime.toLocalDate().atStartOfDay();
        // 这一天的开始时间
        localDateTime.toLocalDate().atTime(LocalTime.MAX);
        // 30小时后
        localDateTime.plusHours(30);
        // 30分钟后
        localDateTime.plusMinutes(30);

        // 转换
        // Date与LocalDateTime之间的转换需要通过Instant
        // LocalDate无法直接转为LocalDateTime，这里将时间设为一天的开始时间
        // LocalTime无法直接转为LocalDateTime，这里将日期设为当天
        // Date Instant
        Instant instant = new Date().toInstant();
        Date date2 = Date.from(Instant.now());
        // Instant LocalDateTime，当前时区
        LocalDateTime l2 = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        Instant instant2 = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant();
        // Date LocalDateTime
        LocalDateTime l3 = LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
        Date date3 = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        // LocalDateTime LocalDate
        LocalDate localDate4 = LocalDateTime.now().toLocalDate();
        LocalDateTime l4 = LocalDate.now().atStartOfDay();
        // LocalDateTime LocalTime
        LocalTime localTime5 = LocalDateTime.now().toLocalTime();
        LocalDateTime l5 = LocalDateTime.of(LocalDate.now(), LocalTime.now());

        // 格式化
        // 指定日期时间
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        // ISO_LOCAL_DATE 2020-03-25
        DateTimeFormatter.ISO_LOCAL_DATE.format(zonedDateTime);
        // ISO_OFFSET_DATE 2020-03-25+08:00
        DateTimeFormatter.ISO_OFFSET_DATE.format(zonedDateTime);
        // ISO_DATE 2020-03-25+08:00
        DateTimeFormatter.ISO_DATE.format(zonedDateTime);
        // ISO_LOCAL_TIME 22:22:55.6747072
        DateTimeFormatter.ISO_LOCAL_TIME.format(zonedDateTime);
        // ISO_OFFSET_TIME 22:22:55.6747072+08:00
        DateTimeFormatter.ISO_OFFSET_TIME.format(zonedDateTime);
        // ISO_TIME 22:22:55.6747072+08:00
        DateTimeFormatter.ISO_TIME.format(zonedDateTime);
        // ISO_LOCAL_DATE_TIME 2020-03-25T22:22:55.6747072
        DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(zonedDateTime);
        // ISO_OFFSET_DATE_TIME 2020-03-25T22:22:55.6747072+08:00
        DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(zonedDateTime);
        // ISO_ZONED_DATE_TIME 2020-03-25T22:22:55.6747072+08:00[Asia/Shanghai]
        DateTimeFormatter.ISO_ZONED_DATE_TIME.format(zonedDateTime);
        // ISO_DATE_TIME 2020-03-25T22:22:55.6747072+08:00[Asia/Shanghai]
        DateTimeFormatter.ISO_DATE_TIME.format(zonedDateTime);
        // ISO_ORDINAL_DATE 2020-085+08:00
        DateTimeFormatter.ISO_ORDINAL_DATE.format(zonedDateTime);
        // ISO_WEEK_DATE 2020-W13-3+08:00
        DateTimeFormatter.ISO_WEEK_DATE.format(zonedDateTime);
        // ISO_INSTANT 2020-03-25T14:22:55.674707200Z
        DateTimeFormatter.ISO_INSTANT.format(zonedDateTime);
        // BASIC_ISO_DATE 20200325+0800
        DateTimeFormatter.BASIC_ISO_DATE.format(zonedDateTime);
        // RFC_1123_DATE_TIME Wed, 25 Mar 2020 22:22:55 +0800
        DateTimeFormatter.RFC_1123_DATE_TIME.format(zonedDateTime);
        // 指定日期时间
        ZonedDateTime now = ZonedDateTime.now();
        // 2020-03-25 22:29:33
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(now);
        // 2020-03-25 等同于DateTimeFormatter.ISO_LOCAL_DATE
        DateTimeFormatter.ofPattern("yyyy-MM-dd").format(now);
        // 2020年03月25日 22时29分33秒
        DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分ss秒").format(now);
        // 2020年03月25日
        DateTimeFormatter.ofPattern("yyyy年MM月dd日").format(now);
        // 2020-03
        DateTimeFormatter.ofPattern("yyyy-MM").format(now);
        // 2020年03月
        DateTimeFormatter.ofPattern("yyyy年MM月").format(now);
        // 20200325
        DateTimeFormatter.ofPattern("yyyyMMdd").format(now);
        // 202003
        DateTimeFormatter.ofPattern("yyyyMM").format(now);

        // 解析
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
            // 2020-03-25
            LocalDate.parse("2020年03月25日", dateTimeFormatter);
            // 2020-03-25
            LocalDate.from(dateTimeFormatter.parse("2020年03月25日"));
            // 2020-03-25
            dateTimeFormatter.parse("2020年03月25日", LocalDate::from);
        } catch (DateTimeException ex) {
            ex.printStackTrace();
        }

        // 比较
        LocalDateTime dateTime1 = LocalDateTime.of(2020, 3, 25, 6, 44, 32);
        LocalDateTime dateTime2 = LocalDateTime.now();
        // 是否为同一天 true
        dateTime1.toLocalDate().equals(dateTime2.toLocalDate());
        // localDateTime1是否在localDateTime2之前 true
        dateTime1.isBefore(dateTime2);
        // localDateTime1是否在localDateTime2之后 false
        dateTime1.isAfter(dateTime2);

        // 间隔
        // Period
        // 获取两个日期时间，表面上的间隔，比如2011年2月1日，和2021年8月11日，日相差了10天，月相差6月，而且只能比较LocalDate，表示表面上年月日的偏移
        LocalDate localDate1 = LocalDate.of(2020, 1, 15);
        LocalDate localDate2 = LocalDate.now();
        Period period = Period.between(localDate1, localDate2);
        // 28
        System.out.println(period.getDays());
        // 8
        System.out.println(period.getMonths());
        // Duration
        // 与Period相比，Duration能得到相差的实际天数、小时数等，但没有年、月。实际上，它是一个基于时间的量，表示一段连续的时间
        LocalDateTime localDateTime1 = LocalDateTime.of(2020, 1, 15, 8, 21, 12);
        LocalDateTime localDateTime2 = LocalDateTime.now();
        Duration between = Duration.between(localDateTime1, localDateTime2);
        // 70
        System.out.println(between.toDays());
        // 1694
        System.out.println(between.toHours());
        // ChronoUnit
        // 与前两个相比，这个才是真正比较日期时间间隔的方式。
        // 0
        localDateTime1.until(localDateTime2, ChronoUnit.YEARS);
        // 或 ChronoUnit.YEARS.between(localDateTime1, localDateTime2)
        // 2
        localDateTime1.until(localDateTime2, ChronoUnit.MONTHS);
        // 70
        localDateTime1.until(localDateTime2, ChronoUnit.DAYS);
        // 1694
        localDateTime1.until(localDateTime2, ChronoUnit.HOURS);
        // 101678
        localDateTime1.until(localDateTime2, ChronoUnit.MINUTES);
        // 6100682
        localDateTime1.until(localDateTime2, ChronoUnit.SECONDS);

        // 获取一段连续的日期或时间
        // [2012-09, 2013-07, 2014-05, 2015-03, 2016-01, 2016-11, 2017-09, 2018-07, 2019-05, 2020-03]
        Stream.iterate(YearMonth.now(), yearMonth -> yearMonth.plusMonths(-10))
                .limit(10)
                .sorted(Comparator.comparing(YearMonth::getYear).thenComparing(YearMonth::getMonthValue))
                .collect(Collectors.toList())
                .forEach(System.out::println);

    }

    /**
     * 取本月第一天
     */
    public static LocalDate firstDayOfThisMonth() {
        LocalDate today = LocalDate.now();
        return today.with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 取本月第N天
     */
    public static LocalDate dayOfThisMonth(int n) {
        LocalDate today = LocalDate.now();
        return today.withDayOfMonth(n);
    }

    /**
     * 取本月最后一天
     */
    public static LocalDate lastDayOfThisMonth() {
        LocalDate today = LocalDate.now();
        return today.with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 取本月第一天的开始时间
     */
    public static LocalDateTime startOfThisMonth() {
        return LocalDateTime.of(firstDayOfThisMonth(), LocalTime.MIN);
    }

    /**
     * 取本月最后一天的结束时间
     */
    public static LocalDateTime endOfThisMonth() {
        return LocalDateTime.of(lastDayOfThisMonth(), LocalTime.MAX);
    }

}
