package com.telegram_bot.bot;

import com.telegram_bot.configurations.BotConfig;
import com.telegram_bot.services.booking.exceptions.ExceedingLimitException;
import com.telegram_bot.services.telegram.facade.TelegramFacade;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class MyBot extends TelegramWebhookBot {

    BotConfig botConfig;
    TelegramFacade telegramFacade;

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        try {
            return telegramFacade.handleUpdate(update);
        } catch (ExceedingLimitException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotPath() {
        return botConfig.getWebHookPath();
    }

    @Override
    public String getBotUsername() {
        return botConfig.getName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }
}
