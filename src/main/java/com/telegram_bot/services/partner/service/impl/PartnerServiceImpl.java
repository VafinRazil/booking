package com.telegram_bot.services.partner.service.impl;

import com.telegram_bot.global.util.service.DateTimeUtil;
import com.telegram_bot.services.booking.exceptions.ExceedingLimitException;
import com.telegram_bot.services.booking.exceptions.TimeIsBusyException;
import com.telegram_bot.services.booking.model.entity.Booking;
import com.telegram_bot.services.booking.service.BookingService;
import com.telegram_bot.services.partner.crud.CRUDPartnerSearchRequestService;
import com.telegram_bot.services.partner.model.entity.PartnerSearchRequest;
import com.telegram_bot.services.partner.service.PartnerService;
import com.telegram_bot.services.partner.util.enums.ApplicationStatus;
import com.telegram_bot.services.user.model.entity.UserEntity;
import com.telegram_bot.services.user.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class PartnerServiceImpl implements PartnerService {

    BookingService bookingService;
    CRUDPartnerSearchRequestService crudPartnerSearchRequestService;
    UserService userService;
    DateTimeUtil dateTimeUtil;

    @Override
    public void createRequestForPartner(
            UserEntity userEntity
            , LocalDateTime localDateTime
    ){
        PartnerSearchRequest partnerSearchRequest = new PartnerSearchRequest();
        partnerSearchRequest.setCreator(userEntity);
        partnerSearchRequest.setApplicationStatus(ApplicationStatus.SENT);
        try {
            Optional<Booking> booking = bookingService.signUpForFreeSlot(localDateTime, userEntity);
            if (booking.isPresent()) {
                partnerSearchRequest.setBooking(booking.get());
                partnerSearchRequest.setDateTimeDispatch(LocalDateTime.now());
                partnerSearchRequest.setDateTimeChangeStatus(LocalDateTime.now());
                userEntity.addPartnerSearchRequest(partnerSearchRequest);
                crudPartnerSearchRequestService.save(partnerSearchRequest);
                userService.saveUser(userEntity);
            }else throw new IllegalArgumentException("Booking is not exist!");
        }catch (Exception e){
            log.info(e.toString());
        }
    }

    @Override
    public List<PartnerSearchRequest> getAllFreeApplicationsByDate(LocalDate localDate) {
        LocalDate lastDayOfWeek = dateTimeUtil.getLastDayOfWeek();
        return crudPartnerSearchRequestService.getFreePartnerSearchRequestsByDate(localDate, lastDayOfWeek);
    }

    @Override
    public List<PartnerSearchRequest> getApplicationsUser(UserEntity userEntity) {
        LocalDate currentDate = LocalDate.now();
        LocalDate lastDayOfWeek = dateTimeUtil.getLastDayOfWeek();
        return crudPartnerSearchRequestService.getPartnerSearchRequestByUser(userEntity, currentDate, lastDayOfWeek);
    }

    @Override
    public void becomePartner(UserEntity user, long partnerSearchRequestId) {
        PartnerSearchRequest partnerSearchRequest = crudPartnerSearchRequestService.getEntityById(partnerSearchRequestId).orElseThrow();
        partnerSearchRequest.setPartner(user);
        crudPartnerSearchRequestService.save(partnerSearchRequest);
    }

    @Override
    public void deleteApplication(long id) {
        PartnerSearchRequest partnerSearchRequest = crudPartnerSearchRequestService.getEntityById(id).orElseThrow();
        if (Optional.ofNullable(partnerSearchRequest.getPartner()).isEmpty()){
            crudPartnerSearchRequestService.delete(partnerSearchRequest);
        }
    }
}
