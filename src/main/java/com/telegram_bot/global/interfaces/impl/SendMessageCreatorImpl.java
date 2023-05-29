package com.telegram_bot.global.interfaces.impl;

import com.telegram_bot.global.interfaces.SendMessageCreator;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Component
public class SendMessageCreatorImpl implements SendMessageCreator {

    @Override
    public SendMessage createSendMessageBody(
            Long chatId
            , InlineKeyboardMarkup inlineKeyboardMarkup
            , String text
    ){
        return SendMessage
                .builder()
                .chatId(chatId)
                .replyMarkup(inlineKeyboardMarkup)
                .text(text)
                .build();
    }

    @Override
    public SendMessage createSendMessageBody(
            Long chatId
            , String text
    ){
        return SendMessage
                .builder()
                .chatId(chatId)
                .text(text)
                .build();
    }
}
