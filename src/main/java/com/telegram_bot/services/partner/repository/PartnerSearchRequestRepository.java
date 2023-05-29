package com.telegram_bot.services.partner.repository;

import com.telegram_bot.services.partner.model.entity.PartnerSearchRequest;
import com.telegram_bot.services.user.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PartnerSearchRequestRepository extends JpaRepository<PartnerSearchRequest, Long> {

    List<PartnerSearchRequest> findPartnerSearchRequestsByDateTimeChangeStatusBetweenAndCreatorOrPartner(
            LocalDateTime startDate
            , LocalDateTime endDate
            , UserEntity creator
            , UserEntity partner);

    List<PartnerSearchRequest> findPartnerSearchRequestsByDateTimeDispatchBetweenAndPartnerIsNull(
            LocalDateTime startDate
            , LocalDateTime endDate);
}
