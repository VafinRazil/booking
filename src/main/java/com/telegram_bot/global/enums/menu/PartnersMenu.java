package com.telegram_bot.global.enums.menu;

public enum PartnersMenu implements TextGetter{

    CREATE_PARTNER_SEARCH_REQUEST("Создать заявку на поиск напарника")
    , SHOW_EXISTS_PARTNER_SEARCH_REQUESTS("Просмотреть существующие заявки")
    , MY_PARTNER_SEARCH_REQUESTS("Мои заявки");

    private final String text;

    PartnersMenu(String text){
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }
}
