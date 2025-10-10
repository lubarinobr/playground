package com.matheus.playground.valueobject.entities;

import com.matheus.playground.valueobject.converters.UserEmailConverter;
import com.matheus.playground.valueobject.valueobjects.UserEmail;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Serializable {

    @EmbeddedId
    private SocialNumberId id;
    @Embedded
    private FullName name;
    @Convert(converter = UserEmailConverter.class)
    private UserEmail email;

}
