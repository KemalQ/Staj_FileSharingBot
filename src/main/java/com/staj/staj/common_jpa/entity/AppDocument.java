package com.staj.staj.common_jpa.entity;

import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_document")
public class AppDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String telegramFieldId;
    private String docName;
    @OneToOne
    private BinaryContent binaryContent;//ссылка на объект BinaryContent
    private String mimeType;
    private Long fileSize;
}
