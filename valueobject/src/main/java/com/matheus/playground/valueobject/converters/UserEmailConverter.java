package com.matheus.playground.valueobject.converters;

import com.matheus.playground.valueobject.valueobjects.UserEmail;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UserEmailConverter implements AttributeConverter<UserEmail, String> {
    @Override
    public String convertToDatabaseColumn(UserEmail userEmail) {
        return userEmail == null ? null : userEmail.email();
    }

    @Override
    public UserEmail convertToEntityAttribute(String userEmail) {
        return userEmail == null ? null : new UserEmail(userEmail);
    }
}
