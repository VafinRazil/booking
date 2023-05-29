package com.telegram_bot.global.util.service.impl;

import com.telegram_bot.configurations.BookingPropertiesConfig;
import com.telegram_bot.global.enums.datetime.DayOfWeekRus;
import com.telegram_bot.global.util.service.DateTimeUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class DateTimeUtilImpl implements DateTimeUtil {

    BookingPropertiesConfig bookingPropertiesConfig;

    @Override
    public List<LocalTime> generateTimes() {
        final LocalTime localTimeBegin = LocalTime.of(bookingPropertiesConfig.getTimeBegin(), 0, 0);
        final int limit = bookingPropertiesConfig.getTimeEnd() - bookingPropertiesConfig.getTimeBegin() + 1;
        return Stream
                .iterate(localTimeBegin, t -> t.plusHours(1))
                .limit(limit)
                .toList();
    }

    @Override
    public List<DayOfWeekRus> generateListDayOfWeekAfterToday() {
        LocalDate localDate = LocalDate.now();
        return DayOfWeekRus.getDaysOfWeekAfter(localDate.getDayOfWeek());
    }

    @Override
    public LocalDate getLastDayOfWeek(){
        final LocalDate currentDate = LocalDate.now();
        DayOfWeek currentDayOfWeek = currentDate.getDayOfWeek();
        int daysBetween = DayOfWeek.SUNDAY.ordinal() - currentDayOfWeek.ordinal();
        return currentDate.plusDays(daysBetween);
    }
}
