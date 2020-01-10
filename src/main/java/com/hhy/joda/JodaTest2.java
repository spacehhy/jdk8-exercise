package com.hhy.joda;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

public class JodaTest2 {

    public static void main(String[] args) {
        DateTime today = new DateTime();
        DateTime tomorrow = today.plusDays(1);
        System.out.println(today.toString("yyyy-MM-dd HH:mm:ss"));
        System.out.println(tomorrow.toString("yyyy-MM-dd HH:mm:ss"));

        System.out.println("--------");

        DateTime d1 = today.withDayOfMonth(1);
        System.out.println(d1.toString("yyyy-MM-dd"));

        System.out.println("--------");

        //当前时区的时间
        LocalDate localDate = new LocalDate();
        LocalDate localDate1 = new LocalDate();
        System.out.println(localDate);

        System.out.println("--------");

        localDate = localDate.plusMonths(1).dayOfMonth().withMaximumValue();
        localDate1 = localDate1.plusMonths(5).dayOfMonth().withMinimumValue();
        System.out.println(localDate);
        System.out.println(localDate1);

        System.out.println("--------");

        //计算2年前第3个月最后1天的日期
        DateTime dateTime = new DateTime();
        DateTime dateTime2 = dateTime.minusYears(2).monthOfYear().setCopy(2).dayOfMonth().withMaximumValue();

        System.out.println(dateTime2.toString("yyyy-MM-dd"));
    }
}
