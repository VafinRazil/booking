package com.telegram_bot.services.partner.service;

import com.telegram_bot.services.booking.exceptions.ExceedingLimitException;
import com.telegram_bot.services.booking.exceptions.TimeIsBusyException;
import com.telegram_bot.services.partner.model.entity.PartnerSearchRequest;
import com.telegram_bot.services.user.model.entity.UserEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PartnerService {

    void createRequestForPartner(UserEntity userEntity, LocalDateTime localDateTime) throws ExceedingLimitException, TimeIsBusyException;

    List<PartnerSearchRequest> getAllFreeApplicationsByDate(LocalDate localDate);

    List<PartnerSearchRequest> getApplicationsUser(UserEntity userEntity);

    void becomePartner(UserEntity user, long partnerSearchRequestId);

    void deleteApplication(long id);
}
