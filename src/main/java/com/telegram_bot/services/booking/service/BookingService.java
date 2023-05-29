package com.telegram_bot.services.booking.service;

import com.telegram_bot.services.booking.exceptions.ExceedingLimitException;
import com.telegram_bot.services.booking.exceptions.TimeIsBusyException;
import com.telegram_bot.services.booking.model.entity.Booking;
import com.telegram_bot.services.user.model.entity.UserEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface BookingService {

    List<LocalTime> getFreeSlotsByDate(LocalDate localDate);

    Optional<Booking> signUpForFreeSlot(LocalDateTime localDateTime, UserEntity user) throws ExceedingLimitException, TimeIsBusyException;

    List<Booking> getBookingsForUserByDate(LocalDate localDate, UserEntity userEntity);

    List<Booking> getBookingsForUserByBetweenDates(LocalDate startDate, LocalDate endDate, UserEntity userEntity);

    Optional<Booking> getBookingById(long id);

    void saveBooking(Booking booking);

    void cancelBooking(long bookingId, UserEntity user);
}
