package com.telegram_bot.services.menu.service.partner.impl;

import com.telegram_bot.global.enums.datetime.DayOfWeekRus;
import com.telegram_bot.global.enums.operation.Operation;
import com.telegram_bot.services.menu.service.partner.MenuPartnerCreator;
import com.telegram_bot.services.partner.model.entity.PartnerSearchRequest;
import com.telegram_bot.services.user.model.entity.UserEntity;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MenuPartnerCreatorImpl implements MenuPartnerCreator {

    @Override
    public InlineKeyboardMarkup createInlineKeyboardMarkupUserApplications(List<PartnerSearchRequest> partnerSearchRequests) {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        for (PartnerSearchRequest partnerSearchRequest:partnerSearchRequests){

            List<InlineKeyboardButton> row = new ArrayList<>();
            List<InlineKeyboardButton> row2 = new ArrayList<>();

            InlineKeyboardButton inlineKeyboardButton =
                    createInlineKeyboardButton(formTextPartnerSearchRequestInfo(partnerSearchRequest), "/as");

            InlineKeyboardButton inlineKeyboardButton2 =
                    createInlineKeyboardButton(
                            "Отменить"
                            , formCallbackDataForCancelPartnerSearchRequest(partnerSearchRequest)
                    );
            row.add(inlineKeyboardButton);
            row2.add(inlineKeyboardButton2);
            rowList.add(row);
            rowList.add(row2);
        }

        return createInlineKeyboardMarkup(rowList);
    }

    @Override
    public InlineKeyboardMarkup createInlineKeyboardMarkupAllApplications(List<PartnerSearchRequest> partnerSearchRequests) {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        for (PartnerSearchRequest partnerSearchRequest:partnerSearchRequests){

            List<InlineKeyboardButton> row = new ArrayList<>();

            InlineKeyboardButton inlineKeyboardButton =
                    createInlineKeyboardButton(
                            formTextFreePartnerSearchRequestInfo(partnerSearchRequest)
                            , formCallbackDataForSelectPartnerSearchRequest(partnerSearchRequest)
                    );

            row.add(inlineKeyboardButton);
            rowList.add(row);
        }


        return createInlineKeyboardMarkup(rowList);
    }

    private String formTextFreePartnerSearchRequestInfo(PartnerSearchRequest partnerSearchRequest){
        Optional<UserEntity> creator = Optional.ofNullable(partnerSearchRequest.getCreator());
        String text = String.format("Дата и время: %s, владелец заявки: %s", partnerSearchRequest.getBooking().getBookingDateTime(), creator.get().getUsername());
        return text;
    }

    private String formTextPartnerSearchRequestInfo(PartnerSearchRequest partnerSearchRequest){
        Optional<UserEntity> partner = Optional.ofNullable(partnerSearchRequest.getPartner());
        String partnerName = partner.isPresent() ? partner.get().getUsername() : "отсутствует";
        String text = String.format("Напарник: %s, дата и время: %s", partnerName, partnerSearchRequest.getBooking().getBookingDateTime());
        return text;
    }

    private String formCallbackDataForCancelPartnerSearchRequest(PartnerSearchRequest partnerSearchRequest){
        return String.format("%s&%s", Operation.CANCEL_PARTNER_SEARCH_REQUEST.name(), partnerSearchRequest.getId());
    }

    private String formCallbackDataForSelectPartnerSearchRequest(PartnerSearchRequest partnerSearchRequest){
        return String.format("%s&%s", Operation.SELECT_PARTNER_SEARCH_REQUEST.name(), partnerSearchRequest.getId());
    }
}
