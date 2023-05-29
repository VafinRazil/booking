package com.telegram_bot.services.user.crud;

import com.telegram_bot.global.crud.CRUDRepository;
import com.telegram_bot.services.user.model.entity.UserEntity;

import java.util.Optional;

public interface CRUDUserService extends CRUDRepository<UserEntity> {
    Optional<UserEntity> getEntityByTelegramId(long userIdInTelegram);
}
