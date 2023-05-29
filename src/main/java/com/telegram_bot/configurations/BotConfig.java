package com.telegram_bot.configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix = "telegram.bot")
public class BotConfig {

    String token;

    String name;

    String webHookPath;
}
