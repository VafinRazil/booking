package com.telegram_bot.global.enums.menu;

public enum BookingsMenu implements TextGetter{

    CANCEL("Отменить бронь")
    , DONT_HAVE_ANY_BOOKINGS("У вас нет бронирований")
    , ALL_BOOKINGS("Все бронирования в промежутке от %s до %s");

    private final String text;

    BookingsMenu(String text){
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }

}
