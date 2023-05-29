package com.telegram_bot.global.interfaces;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public interface InlineKeyboardMarkupCreator {

    default InlineKeyboardMarkup createInlineKeyboardMarkup(List<List<InlineKeyboardButton>> rowList){

        return InlineKeyboardMarkup
                .builder()
                .keyboard(rowList)
                .build();
    }
}
