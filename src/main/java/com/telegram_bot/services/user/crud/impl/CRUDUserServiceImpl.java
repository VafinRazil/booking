package com.telegram_bot.services.user.crud.impl;

import com.telegram_bot.services.user.crud.CRUDUserService;
import com.telegram_bot.services.user.model.entity.UserEntity;
import com.telegram_bot.services.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CRUDUserServiceImpl implements CRUDUserService {

    UserRepository userRepository;

    @Override
    public Optional<UserEntity> save(UserEntity entity) {
        return Optional.ofNullable(userRepository.save(entity));
    }

    @Override
    public Optional<UserEntity> update(UserEntity entity) {
        return Optional.ofNullable(userRepository.save(entity));
    }

    @Override
    public void delete(UserEntity entity) {
        userRepository.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserEntity> getEntityById(long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserEntity> getEntityByTelegramId(long userIdInTelegram) {
        return userRepository.findByUserIdInTelegram(userIdInTelegram);
    }
}
