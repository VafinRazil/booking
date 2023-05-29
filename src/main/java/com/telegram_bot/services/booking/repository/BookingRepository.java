package com.telegram_bot.services.booking.repository;

import com.telegram_bot.services.booking.model.entity.Booking;
import com.telegram_bot.services.user.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByBookingDateTime(LocalDateTime bookingDateTime);

    List<Booking> getBookingsByBookingDateTimeBetweenAndWhoBookedContains(LocalDateTime startDate, LocalDateTime finalDate, UserEntity whoBooked);
}
