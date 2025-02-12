package com.staj.staj.common_jpa.entity;
import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_photo")
public class AppPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String telegramFieldId;
    @OneToOne
    private BinaryContent binaryContent;//ссылка на объект BinaryContent
    private Long fileSize;
}
