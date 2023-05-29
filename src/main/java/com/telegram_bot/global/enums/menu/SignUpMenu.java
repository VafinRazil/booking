package com.telegram_bot.global.enums.menu;

public enum SignUpMenu implements TextGetter{

    BOOKING_LIMIT_EXCEEDED("Превышен лимит броней! Бронирование невозможно.")
    , NO_FREE_SLOTS("Нет свободных слотов на %s.")
    , SUCCESSFUL_SLOT_SELECTION("Слот выбран. Вы записаны на %s.");

    private final String text;

    SignUpMenu(String text){
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }
}
