package com.telegram_bot.services.menu.service.partner;

import com.telegram_bot.global.interfaces.InlineKeyboardButtonCreator;
import com.telegram_bot.global.interfaces.InlineKeyboardMarkupCreator;
import com.telegram_bot.services.partner.model.entity.PartnerSearchRequest;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

public interface MenuPartnerCreator
        extends InlineKeyboardButtonCreator
                , InlineKeyboardMarkupCreator {

    InlineKeyboardMarkup createInlineKeyboardMarkupUserApplications(
            List<PartnerSearchRequest> partnerSearchRequests);

    InlineKeyboardMarkup createInlineKeyboardMarkupAllApplications(
            List<PartnerSearchRequest> partnerSearchRequests);
}
