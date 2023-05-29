package com.telegram_bot.controllers;

import com.telegram_bot.bot.MyBot;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class WebhookController {

    MyBot myBot;

    @PostMapping("/")
    public BotApiMethod<?> firstController(@RequestBody Update update){
        return myBot.onWebhookUpdateReceived(update);
    }
}
