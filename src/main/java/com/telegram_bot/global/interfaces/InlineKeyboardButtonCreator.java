package com.telegram_bot.global.interfaces;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public interface InlineKeyboardButtonCreator {

    default InlineKeyboardButton createInlineKeyboardButton(String text, String callbackData) {
        return InlineKeyboardButton
                        .builder()
                        .text(text)
                        .callbackData(callbackData)
                        .build();
    }
}
