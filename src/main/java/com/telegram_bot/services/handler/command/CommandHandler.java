package com.telegram_bot.services.handler.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface CommandHandler {

    SendMessage handleCommandAndReturnMessage(String command);
}
