package com.telegram_bot.configurations;

import lombok.Data;
import org.springframework.context.annotation.Configuration;


@Configuration
@Data
public class BookingPropertiesConfig {

    int limitPerDay = 2;

    int timeBegin = 9;

    int timeEnd = 21;
}
