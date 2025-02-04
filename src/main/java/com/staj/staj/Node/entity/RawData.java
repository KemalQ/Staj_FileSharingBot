package com.staj.staj.Node.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

//@Data
@Setter
@Getter
@EqualsAndHashCode(exclude = "id")//исключение для id, при генерации G&S значение id учитываться не будут
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "raw_data")//возможен не правильный импорт
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)//возможен не правильный импорт
public class RawData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Type(type="jsonb")
    @Column(columnDefinition = "jsonb")
    private Update event;
}
