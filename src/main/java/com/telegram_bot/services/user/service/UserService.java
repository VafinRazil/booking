package com.telegram_bot.services.user.service;

import com.telegram_bot.services.user.model.entity.UserEntity;

import java.time.LocalDate;
import java.util.Optional;

public interface UserService {

    Optional<UserEntity> createNewUser(org.telegram.telegrambots.meta.api.objects.User telegramUser);

    Optional<UserEntity> getUserByTelegramId(long userIdInTelegram);

    Optional<UserEntity> getUserById(long userId);

    Optional<UserEntity> saveUser(UserEntity user);

    boolean bookingLimitNotExceeded(UserEntity user, LocalDate date);
}
