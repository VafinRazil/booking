package com.telegram_bot.services.booking.exceptions;

public class TimeIsBusyException extends Exception{
    public TimeIsBusyException(String message){
        super(message);
    }
}
