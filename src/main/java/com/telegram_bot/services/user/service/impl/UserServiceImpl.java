package com.telegram_bot.services.user.service.impl;

import com.telegram_bot.configurations.BookingPropertiesConfig;
import com.telegram_bot.services.booking.model.entity.Booking;
import com.telegram_bot.services.booking.service.BookingService;
import com.telegram_bot.services.user.crud.CRUDUserService;
import com.telegram_bot.services.user.model.entity.UserEntity;
import com.telegram_bot.services.user.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final CRUDUserService crudUserService;
    private final BookingService bookingService;
    private final BookingPropertiesConfig bookingPropertiesConfig;

    public UserServiceImpl(
            CRUDUserService crudUserService
            , @Lazy BookingService bookingService
            , BookingPropertiesConfig bookingPropertiesConfig
    ) {
        this.crudUserService = crudUserService;
        this.bookingService = bookingService;
        this.bookingPropertiesConfig = bookingPropertiesConfig;
    }

    @Override
    public Optional<UserEntity> createNewUser(User telegramUser) {
        log.info("Create new user");
        UserEntity userEntity = new UserEntity();
        userEntity.setUserIdInTelegram(telegramUser.getId());
        userEntity.setUsername(telegramUser.getUserName());
        return crudUserService.save(userEntity);
    }

    @Override
    public Optional<UserEntity> getUserByTelegramId(long userIdInTelegram) {
        return crudUserService.getEntityByTelegramId(userIdInTelegram);
    }

    @Override
    public Optional<UserEntity> getUserById(long userId) {
        return crudUserService.getEntityById(userId);
    }

    @Override
    public Optional<UserEntity> saveUser(UserEntity user) {
        return crudUserService.save(user);
    }

    @Override
    public boolean bookingLimitNotExceeded(UserEntity user, LocalDate date) {
        List<Booking> bookings = bookingService.getBookingsForUserByDate(date, user);
        log.info("Found {} bookings in {} for user {}, limit {}", bookings.size(), date, user.getUsername(), bookingPropertiesConfig.getLimitPerDay());
        return (bookings.size() < bookingPropertiesConfig.getLimitPerDay());
    }

}
