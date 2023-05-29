package com.telegram_bot.services.menu.service.booking.impl;

import com.telegram_bot.global.enums.menu.BookingsMenu;
import com.telegram_bot.services.booking.model.entity.Booking;
import com.telegram_bot.services.menu.service.booking.BookingMenuCreator;
import com.telegram_bot.services.user.model.entity.UserEntity;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookingMenuCreatorImpl implements BookingMenuCreator {

    @Override
    public InlineKeyboardMarkup createInlineKeyboardMarkupList(List<Booking> bookings, UserEntity user) {

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        for (Booking booking:bookings){
            List<InlineKeyboardButton> row = new ArrayList<>();

            String text = formCallbackDataForRowBooking(booking, user);

            InlineKeyboardButton inlineKeyboardButton =
                    createInlineKeyboardButton(text, Long.toString(booking.getId()));

            InlineKeyboardButton inlineKeyboardButton2 =
                    createInlineKeyboardButton(
                            BookingsMenu.CANCEL.getText()
                            , formCallbackDataForCancel(booking.getId()));

            row.add(inlineKeyboardButton);
            row.add(inlineKeyboardButton2);
            rowList.add(row);
        }

        return createInlineKeyboardMarkup(rowList);
    }

    private  String formCallbackDataForRowBooking(Booking booking, UserEntity reqOwner){
        String text = "";
        final String dateStr = booking.getBookingDateTime().toLocalDate().toString();
        final String timeStr = booking.getBookingDateTime().toLocalTime().toString();
        if (booking.getWhoBooked().size() > 1){
            UserEntity partner = getPartner(booking, reqOwner);
            text = String.format("Дата: %s, время: %s, напарник: %s", dateStr, timeStr, partner.getUsername());
        } else{
            text = String.format("Дата: %s, время: %s", dateStr, timeStr);
        }
        return text;
    }

    private String formCallbackDataForCancel(Long id){
        return String.format("%s&%s", BookingsMenu.CANCEL.name(), id);
    }

    private UserEntity getPartner(Booking booking, UserEntity reqOwner){
        return booking.getWhoBooked().stream().filter(user -> user.getId() != reqOwner.getId()).findFirst().get();
    }
}
