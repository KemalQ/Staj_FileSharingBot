package com.staj.staj.common_jpa.entity;

import com.staj.staj.common_jpa.entity.enums.UserState;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@EqualsAndHashCode(exclude = "id")//исключение для id, при генерации G&S значение id учитываться не будут
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "app_user")//возможен не правильный импорт
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long telegramUserId;//id пользователя в самом телеграмме
    @CreationTimestamp
    private LocalDateTime firstLoginDate;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private Boolean isActive;
    @Enumerated(EnumType.STRING)
    private UserState state;
}
