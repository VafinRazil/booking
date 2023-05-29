package com.telegram_bot.services.telegram.facade;

import com.telegram_bot.services.booking.exceptions.ExceedingLimitException;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramFacade {

    BotApiMethod<?> handleUpdate(Update update) throws ExceedingLimitException;

}
