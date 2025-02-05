package com.staj.staj.common_jpa.dao;

import com.staj.staj.common_jpa.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserDAO extends JpaRepository<AppUser, Long> {
    AppUser findAppUserByTelegramUserId(Long id);//для проверки пользователя на присут в БД
}
