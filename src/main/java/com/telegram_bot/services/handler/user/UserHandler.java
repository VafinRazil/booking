package com.telegram_bot.services.handler.user;

import com.telegram_bot.services.user.model.entity.UserEntity;
import com.telegram_bot.services.user.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class UserHandler {

    UserService userService;

    public void handleUserInfo(User user){
        Optional<UserEntity> userFromDB = userService.getUserByTelegramId(user.getId());
        if (userFromDB.isPresent()){
            if (usernameIsChanged(userFromDB.get(), user)){
                userFromDB.get().setUsername(user.getUserName());
                log.info("Set new username {} to user with telegram id {}", user.getUserName(), user.getId());
                userService.saveUser(userFromDB.get());
            }
        }else {
            userService.createNewUser(user);
        }
    }

    private boolean usernameIsChanged(
            UserEntity userEntityFromDB
            , User user
    ){
        boolean result = !userEntityFromDB.getUsername().equals(user.getUserName());
        log.info("Username is changed: {}", result);
        return result;
    }
}
