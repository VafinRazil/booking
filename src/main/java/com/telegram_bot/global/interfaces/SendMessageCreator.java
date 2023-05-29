package com.telegram_bot.global.interfaces;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface SendMessageCreator {

    SendMessage createSendMessageBody(
            Long chatId
            , InlineKeyboardMarkup inlineKeyboardMarkup
            , String text
    );

    SendMessage createSendMessageBody(
            Long chatId
            , String text
    );
}
