package com.telegram_bot.global.enums.menu;

public enum StartMenu implements TextGetter{

    MY_BOOKINGS("Мои бронирования")
    , SIGN_UP_FREE_SLOT("Записаться на свободный слот")
    , PARTNERS("Напарники");

    private String text;


    StartMenu(String text){
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }

}
