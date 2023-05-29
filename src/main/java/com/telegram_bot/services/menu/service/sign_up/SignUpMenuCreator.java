package com.telegram_bot.services.menu.service.sign_up;

import com.telegram_bot.global.interfaces.InlineKeyboardButtonCreator;
import com.telegram_bot.global.interfaces.InlineKeyboardMarkupCreator;
import com.telegram_bot.global.enums.datetime.DayOfWeekRus;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface SignUpMenuCreator
        extends InlineKeyboardButtonCreator
        , InlineKeyboardMarkupCreator {

    InlineKeyboardMarkup createInlineKeyboardMarkupDaysOfWeek(List<DayOfWeekRus> values);

    InlineKeyboardMarkup createInlineKeyboardMarkupDaysOfWeekForSearchPartner(List<DayOfWeekRus> values);

    InlineKeyboardMarkup createInlineKeyboardMarkupFreeSlots(List<LocalTime> values, LocalDate date);

    InlineKeyboardMarkup createInlineKeyboardMarkupFreeSlotsForPartnerSearch(List<LocalTime> values, LocalDate date);
}
