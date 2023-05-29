package com.telegram_bot.services.booking.exceptions;

public class ExceedingLimitException extends Exception{

    public ExceedingLimitException(String message){
        super(message);
    }
}
