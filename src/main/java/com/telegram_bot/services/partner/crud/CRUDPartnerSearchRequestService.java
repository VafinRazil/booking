package com.telegram_bot.services.partner.crud;

import com.telegram_bot.global.crud.CRUDRepository;
import com.telegram_bot.services.partner.model.entity.PartnerSearchRequest;
import com.telegram_bot.services.user.model.entity.UserEntity;

import java.time.LocalDate;
import java.util.List;

public interface CRUDPartnerSearchRequestService extends CRUDRepository<PartnerSearchRequest> {

    List<PartnerSearchRequest> getPartnerSearchRequestByUser(UserEntity user, LocalDate localDate, LocalDate lastDayOfWeek);

    List<PartnerSearchRequest> getFreePartnerSearchRequestsByDate(LocalDate localDate, LocalDate lastDayOfWeek);
}
