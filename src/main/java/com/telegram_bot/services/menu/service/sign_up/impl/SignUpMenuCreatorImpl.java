package com.telegram_bot.services.menu.service.sign_up.impl;

import com.telegram_bot.global.enums.datetime.DayOfWeekRus;
import com.telegram_bot.global.enums.menu.PartnersMenu;
import com.telegram_bot.global.enums.operation.Operation;
import com.telegram_bot.services.menu.service.sign_up.SignUpMenuCreator;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class SignUpMenuCreatorImpl implements SignUpMenuCreator {

    @Override
    public InlineKeyboardMarkup createInlineKeyboardMarkupFreeSlots(List<LocalTime> freeTimes, LocalDate date) {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        for (LocalTime time:freeTimes){
            List<InlineKeyboardButton> row = new ArrayList<>();

            InlineKeyboardButton inlineKeyboardButton =
                    createInlineKeyboardButton(time.toString(), formCallbackDataForBooking(time.atDate(date)));

            row.add(inlineKeyboardButton);

            rowList.add(row);
        }

        return createInlineKeyboardMarkup(rowList);
    }

    @Override
    public InlineKeyboardMarkup createInlineKeyboardMarkupFreeSlotsForPartnerSearch(
            List<LocalTime> freeTimes
            , LocalDate date
    ) {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        for (LocalTime time:freeTimes){
            List<InlineKeyboardButton> row = new ArrayList<>();

            InlineKeyboardButton inlineKeyboardButton =
                    createInlineKeyboardButton(time.toString(), formCallbackDataForSelectTimePartnerSearchReq(time.atDate(date)));

            row.add(inlineKeyboardButton);

            rowList.add(row);
        }

        return createInlineKeyboardMarkup(rowList);
    }

    @Override
    public InlineKeyboardMarkup createInlineKeyboardMarkupDaysOfWeek(List<DayOfWeekRus> daysOfWeekRus) {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        for (int i = 0; i < daysOfWeekRus.size(); i++){

            LocalDate date = LocalDate.now().plusDays(i);
            DayOfWeekRus dayOfWeek = daysOfWeekRus.get(i);
            List<InlineKeyboardButton> row = new ArrayList<>();

            InlineKeyboardButton inlineKeyboardButton =
                    createInlineKeyboardButton(dayOfWeek.getRusValue(), formCallbackDataForDayOfWeekSelection(date));

            row.add(inlineKeyboardButton);

            rowList.add(row);
        }

        return createInlineKeyboardMarkup(rowList);
    }

    @Override
    public InlineKeyboardMarkup createInlineKeyboardMarkupDaysOfWeekForSearchPartner(
            List<DayOfWeekRus> daysOfWeekRus
    ) {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        for (int i = 0; i < daysOfWeekRus.size(); i++){

            LocalDate date = LocalDate.now().plusDays(i);
            DayOfWeekRus dayOfWeek = daysOfWeekRus.get(i);
            List<InlineKeyboardButton> row = new ArrayList<>();

            InlineKeyboardButton inlineKeyboardButton =
                    createInlineKeyboardButton(dayOfWeek.getRusValue(), formCallbackDataForSearchPartner(date));

            row.add(inlineKeyboardButton);

            rowList.add(row);
        }

        return createInlineKeyboardMarkup(rowList);
    }

    private String formCallbackDataForDayOfWeekSelection(LocalDate date){
        return String.format("%s&%s", Operation.DAY_OF_WEEK_SELECTION.name(), date);
    }

    private String formCallbackDataForBooking(LocalDateTime localDateTime){
        return String.format("%s&%s", Operation.BOOKING.name(), localDateTime);
    }

    private String formCallbackDataForSelectTimePartnerSearchReq(LocalDateTime localDateTime){
        return String.format("%s&%s", Operation.SELECT_TIME_PARTNER_SEARCH_REQUEST.name(), localDateTime);
    }

    private String formCallbackDataForSearchPartner(LocalDate localDate){
        return String.format("%s&%s", Operation.SELECT_DAY_OF_WEEK_PARTNER_SEARCH_REQUEST.name(), localDate);
    }
}
