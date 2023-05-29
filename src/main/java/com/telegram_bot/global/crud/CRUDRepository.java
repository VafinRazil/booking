package com.telegram_bot.global.crud;

import java.util.Optional;

public interface CRUDRepository<T> {

    Optional<T> save(T entity);

    Optional<T> update(T entity);

    void delete(T entity);

    Optional<T> getEntityById(long id);
}
