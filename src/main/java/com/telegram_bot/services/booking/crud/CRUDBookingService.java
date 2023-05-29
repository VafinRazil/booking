package com.telegram_bot.services.booking.crud;

import com.telegram_bot.global.crud.CRUDRepository;
import com.telegram_bot.services.booking.model.entity.Booking;
import com.telegram_bot.services.user.model.entity.UserEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CRUDBookingService extends CRUDRepository<Booking> {

    Optional<Booking> findByBookingDateTime(LocalDateTime localDateTime);

    List<Booking> findBookingsByUserAndDateTime(UserEntity user, LocalDate bookingDate);

    List<Booking> findBookingsByUserAndBetweenDates(UserEntity user, LocalDate startDate, LocalDate endDate);
}
