package com.telegram_bot.services.booking.crud.impl;

import com.telegram_bot.services.booking.crud.CRUDBookingService;
import com.telegram_bot.services.booking.model.entity.Booking;
import com.telegram_bot.services.booking.repository.BookingRepository;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class CRUDBookingServiceImpl implements CRUDBookingService {

    BookingRepository bookingRepository;

    @Override
    public Optional<Booking> save(Booking entity) {
        return Optional.ofNullable(bookingRepository.save(entity));
    }

    @Override
    public Optional<Booking> update(Booking entity) {
        return Optional.ofNullable(bookingRepository.save(entity));
    }

    @Override
    public void delete(Booking entity) {
        bookingRepository.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Booking> getEntityById(long id) {
        return bookingRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Booking> findByBookingDateTime(LocalDateTime localDateTime) {
        return bookingRepository.findByBookingDateTime(localDateTime);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> findBookingsByUserAndDateTime(UserEntity user, LocalDate bookingDate) {
        return bookingRepository.getBookingsByBookingDateTimeBetweenAndWhoBookedContains(bookingDate.atStartOfDay(), bookingDate.atTime(LocalTime.MAX), user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> findBookingsByUserAndBetweenDates(UserEntity user, LocalDate startDate, LocalDate endDate) {
        return bookingRepository.getBookingsByBookingDateTimeBetweenAndWhoBookedContains(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX), user);
    }
}
