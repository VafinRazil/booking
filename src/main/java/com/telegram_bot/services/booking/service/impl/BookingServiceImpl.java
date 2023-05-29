package com.telegram_bot.services.booking.service.impl;

import com.telegram_bot.configurations.BookingPropertiesConfig;
import com.telegram_bot.global.util.service.DateTimeUtil;
import com.telegram_bot.services.booking.crud.CRUDBookingService;
import com.telegram_bot.services.booking.exceptions.ExceedingLimitException;
import com.telegram_bot.services.booking.exceptions.TimeIsBusyException;
import com.telegram_bot.services.booking.model.entity.Booking;
import com.telegram_bot.services.booking.service.BookingService;
import com.telegram_bot.services.booking.util.enums.BookingStatus;
import com.telegram_bot.services.user.model.entity.UserEntity;
import com.telegram_bot.services.user.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class BookingServiceImpl implements BookingService {

    UserService userService;
    CRUDBookingService crudBookingService;
    DateTimeUtil dateTimeUtil;


    @Override
    public List<LocalTime> getFreeSlotsByDate(LocalDate localDate) {
        List<LocalTime> generatedTimes = dateTimeUtil.generateTimes();
        log.info("Date {}", localDate.toString());
        return removeBusyTimes(generatedTimes, localDate);
    }

    @Override
    public Optional<Booking> signUpForFreeSlot(
            LocalDateTime localDateTime
            , UserEntity user
    ) throws ExceedingLimitException, TimeIsBusyException {
        if (timeFree(localDateTime)){
            if (userService.bookingLimitNotExceeded(user, localDateTime.toLocalDate())) {
                Booking booking = new Booking();
                booking.setWhoBooked(Collections.singleton(user));
                booking.setDateTimeSent(LocalDateTime.now());
                booking.setBookingDateTime(localDateTime);
                booking.setBookingStatus(BookingStatus.BOOKED);
                return crudBookingService.save(booking);
            }else {
                throw new ExceedingLimitException("Exceeding limit booking");
            }
        }throw new TimeIsBusyException("Time is busy");
    }

    @Override
    public List<Booking> getBookingsForUserByDate(LocalDate localDate, UserEntity userEntity) {
        List<Booking> bookings = crudBookingService.findBookingsByUserAndDateTime(userEntity, localDate);
        bookings.sort(Comparator.comparing(Booking::getBookingDateTime));
        return bookings;
    }

    @Override
    public List<Booking> getBookingsForUserByBetweenDates(
            LocalDate startDate
            , LocalDate endDate
            , UserEntity userEntity
    ){
        List<Booking> bookings = crudBookingService.findBookingsByUserAndBetweenDates(userEntity, startDate, endDate);
        bookings.sort(Comparator.comparing(Booking::getBookingDateTime));
        return bookings;
    }

    @Override
    public Optional<Booking> getBookingById(long id) {
        return crudBookingService.getEntityById(id);
    }

    @Override
    public void saveBooking(Booking booking) {
        crudBookingService.save(booking);
    }

    @Override
    //todo нужно будет реализовать логику удаления партнера
    public void cancelBooking(long bookingId, UserEntity user) {
        Booking booking = crudBookingService.getEntityById(bookingId).orElseThrow();
        booking.removeUserWhoBooked(user);
        if (booking.getWhoBooked().size() == 0){
            booking.setBookingStatus(BookingStatus.REFUSAL);
            booking.setDateTimeSent(LocalDateTime.now());
        }
        crudBookingService.save(booking);
    }

    private List<LocalTime> removeBusyTimes(List<LocalTime> timeList, final LocalDate localDate){
        return timeList
                .stream()
                .filter(time -> timeFree(LocalDateTime.of(localDate, time)))
                .toList();
    }

    private boolean timeFree(LocalDateTime localDateTime){
        Optional<Booking> booking = crudBookingService.findByBookingDateTime(localDateTime);
        boolean haveBookingInThisTime = booking.isPresent();
        log.info("Datetime {} is busy: {}", localDateTime, haveBookingInThisTime);
        return !haveBookingInThisTime || booking.get().getBookingStatus() != BookingStatus.BOOKED;
    }

}
