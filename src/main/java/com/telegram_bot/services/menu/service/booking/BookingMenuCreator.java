package com.telegram_bot.services.menu.service.booking;

import com.telegram_bot.global.interfaces.InlineKeyboardButtonCreator;
import com.telegram_bot.global.interfaces.InlineKeyboardMarkupCreator;
import com.telegram_bot.services.booking.model.entity.Booking;
import com.telegram_bot.services.user.model.entity.UserEntity;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

public interface BookingMenuCreator
        extends InlineKeyboardButtonCreator
            , InlineKeyboardMarkupCreator {
    InlineKeyboardMarkup createInlineKeyboardMarkupList(List<Booking> bookings, UserEntity user);
}
