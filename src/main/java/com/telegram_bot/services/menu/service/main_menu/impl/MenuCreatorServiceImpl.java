package com.telegram_bot.services.menu.service.main_menu.impl;

import com.telegram_bot.global.enums.menu.StartMenu;
import com.telegram_bot.global.enums.menu.TextGetter;
import com.telegram_bot.services.menu.service.main_menu.MenuCreatorService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class MenuCreatorServiceImpl implements MenuCreatorService {

    @Override
    public <E extends Enum<E> & TextGetter> InlineKeyboardMarkup createInlineKeyboardMarkup(Class<E> e){
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        for (TextGetter enumValue:e.getEnumConstants()){
            List<InlineKeyboardButton> row = new ArrayList<>();

            InlineKeyboardButton inlineKeyboardButton =
                    createInlineKeyboardButton(enumValue.getText(), enumValue.name());

            row.add(inlineKeyboardButton);
            rowList.add(row);
        }

        return createInlineKeyboardMarkup(rowList);
    }
}
