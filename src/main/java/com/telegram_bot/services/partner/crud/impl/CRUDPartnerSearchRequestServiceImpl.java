package com.telegram_bot.services.partner.crud.impl;

import com.telegram_bot.services.partner.crud.CRUDPartnerSearchRequestService;
import com.telegram_bot.services.partner.model.entity.PartnerSearchRequest;
import com.telegram_bot.services.partner.repository.PartnerSearchRequestRepository;
import com.telegram_bot.services.user.model.entity.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class CRUDPartnerSearchRequestServiceImpl implements CRUDPartnerSearchRequestService {

    PartnerSearchRequestRepository partnerSearchRequestRepository;

    @Override
    public Optional<PartnerSearchRequest> save(PartnerSearchRequest entity) {
        return Optional.ofNullable(partnerSearchRequestRepository.save(entity));
    }

    @Override
    public Optional<PartnerSearchRequest> update(PartnerSearchRequest entity) {
        return Optional.ofNullable(partnerSearchRequestRepository.save(entity));
    }

    @Override
    public void delete(PartnerSearchRequest entity) {
        partnerSearchRequestRepository.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PartnerSearchRequest> getEntityById(long id) {
        return partnerSearchRequestRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PartnerSearchRequest> getPartnerSearchRequestByUser(
            UserEntity user
            , LocalDate localDate
            , LocalDate lastDayOfWeek
    ) {
        return partnerSearchRequestRepository
                .findPartnerSearchRequestsByDateTimeChangeStatusBetweenAndCreatorOrPartner(
                        localDate.atStartOfDay()
                        , lastDayOfWeek.atTime(LocalTime.MAX)
                        , user
                        , user
                );
    }

    @Override
    @Transactional(readOnly = true)
    public List<PartnerSearchRequest> getFreePartnerSearchRequestsByDate(LocalDate localDate, LocalDate lastDayOfWeek) {
        return partnerSearchRequestRepository
                .findPartnerSearchRequestsByDateTimeDispatchBetweenAndPartnerIsNull(
                        localDate.atStartOfDay()
                        , lastDayOfWeek.atTime(LocalTime.MAX)
                );
    }
}
