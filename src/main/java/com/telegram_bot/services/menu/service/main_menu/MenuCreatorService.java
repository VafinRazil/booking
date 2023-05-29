package com.telegram_bot.services.menu.service.main_menu;


import com.telegram_bot.global.enums.menu.TextGetter;
import com.telegram_bot.global.interfaces.InlineKeyboardButtonCreator;
import com.telegram_bot.global.interfaces.InlineKeyboardMarkupCreator;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface MenuCreatorService extends InlineKeyboardButtonCreator, InlineKeyboardMarkupCreator {
    <E extends Enum<E> & TextGetter> InlineKeyboardMarkup createInlineKeyboardMarkup(Class<E> e);
}
