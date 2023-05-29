package com.telegram_bot.global.util.service;

import com.telegram_bot.global.enums.datetime.DayOfWeekRus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface DateTimeUtil {

    List<LocalTime> generateTimes();

    List<DayOfWeekRus> generateListDayOfWeekAfterToday();

    LocalDate getLastDayOfWeek();
}
