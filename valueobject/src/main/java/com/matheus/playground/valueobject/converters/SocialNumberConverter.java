package com.matheus.playground.valueobject.converters;

import com.matheus.playground.valueobject.valueobjects.SocialNumber;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SocialNumberConverter implements AttributeConverter<SocialNumber, String> {

    @Override
    public String convertToDatabaseColumn(SocialNumber socialNumber) {
        return socialNumber == null ? null : socialNumber.value();
    }

    @Override
    public SocialNumber convertToEntityAttribute(String socialNumber) {
        return socialNumber == null ? null : new SocialNumber(socialNumber);
    }
}
