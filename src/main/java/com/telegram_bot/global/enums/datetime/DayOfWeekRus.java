package com.telegram_bot.global.enums.datetime;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public enum DayOfWeekRus {

    MONDAY("Понедельник"),
    TUESDAY("Вторник"),
    WEDNESDAY("Среда"),
    THURSDAY("Четверг"),
    FRIDAY("Пятница"),
    SATURDAY("Суббота"),
    SUNDAY("Воскресенье");

    private final String rusValue;

    DayOfWeekRus(String rusValue){
        this.rusValue = rusValue;
    }

    public String getRusValue() {
        return rusValue;
    }

    public static List<DayOfWeekRus> getDaysOfWeekAfter(DayOfWeek dayOfWeek){
        List<DayOfWeekRus> daysOfWeek = new ArrayList<>();
        for (DayOfWeekRus dayOfWeekRus:DayOfWeekRus.values()){
            if (dayOfWeekRus.ordinal() >= dayOfWeek.ordinal()){
                daysOfWeek.add(dayOfWeekRus);
            }
        }return daysOfWeek;
    }
}
