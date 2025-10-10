package com.matheus.playground.valueobject.entities;

import com.matheus.playground.valueobject.converters.SocialNumberConverter;
import com.matheus.playground.valueobject.valueobjects.SocialNumber;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialNumberId implements Serializable {

    @Convert(converter = SocialNumberConverter.class)
    private SocialNumber socialNumber;

}


