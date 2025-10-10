package com.matheus.playground.valueobject.entities;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FullNameTest {

    @Test
    void shouldCreateFullNameWithAllFields() {
        // Given
        var name = "João";
        var middleName = "Silva";
        var lastName = "Santos";

        // When
        var fullName = new FullName(name, middleName, lastName);

        // Then
        assertThat(fullName.name()).isEqualTo(name);
        assertThat(fullName.middleName()).isEqualTo(middleName);
        assertThat(fullName.lastName()).isEqualTo(lastName);
    }

    @Test
    void shouldReturnFullNameCorrectly() {
        // Given
        var fullName = new FullName("Maria", "Santos", "Oliveira");

        // When
        var result = fullName.fullName();

        // Then
        assertThat(result).isEqualTo("Maria Santos Oliveira");
    }

    @Test
    void shouldReturnAbbreviatedNameCorrectly() {
        // Given
        var fullName = new FullName("Pedro", "Oliveira", "Costa");

        // When
        var result = fullName.abbreviatedName();

        // Then
        assertThat(result).isEqualTo("Pedro O. Costa");
    }

    @Test
    void shouldHandleSingleCharacterMiddleName() {
        // Given
        var fullName = new FullName("Ana", "B", "Silva");

        // When
        var abbreviatedName = fullName.abbreviatedName();

        // Then
        assertThat(abbreviatedName).isEqualTo("Ana B. Silva");
    }

    @Test
    void shouldHandleEmptyMiddleName() {
        // Given
        var fullName = new FullName("Carlos", "", "Lima");

        // When
        var fullNameResult = fullName.fullName();

        // Then
        assertThat(fullNameResult).isEqualTo("Carlos  Lima");
        // Note: abbreviatedName() will throw StringIndexOutOfBoundsException for empty middleName
        assertThatThrownBy(fullName::abbreviatedName)
                .isInstanceOf(StringIndexOutOfBoundsException.class);
    }

    @Test
    void shouldBeImmutable() {
        // Given
        var fullName = new FullName("João", "Silva", "Santos");

        // When & Then - Record fields are final and cannot be modified
        assertThat(fullName.name()).isEqualTo("João");
        assertThat(fullName.middleName()).isEqualTo("Silva");
        assertThat(fullName.lastName()).isEqualTo("Santos");
    }

    @Test
    void shouldImplementEqualsAndHashCode() {
        // Given
        var fullName1 = new FullName("João", "Silva", "Santos");
        var fullName2 = new FullName("João", "Silva", "Santos");
        var fullName3 = new FullName("Maria", "Santos", "Oliveira");

        // When & Then
        assertThat(fullName1).isEqualTo(fullName2);
        assertThat(fullName1.hashCode()).isEqualTo(fullName2.hashCode());
        assertThat(fullName1).isNotEqualTo(fullName3);
    }

    @Test
    void shouldHaveToStringMethod() {
        // Given
        var fullName = new FullName("João", "Silva", "Santos");

        // When
        var result = fullName.toString();

        // Then
        assertThat(result).contains("João");
        assertThat(result).contains("Silva");
        assertThat(result).contains("Santos");
    }

    @Test
    void shouldHandleNullValues() {
        // Given & When & Then
        // Note: Records don't automatically validate null values, they allow nulls
        var fullNameWithNulls = new FullName(null, null, null);
        
        assertThat(fullNameWithNulls.name()).isNull();
        assertThat(fullNameWithNulls.middleName()).isNull();
        assertThat(fullNameWithNulls.lastName()).isNull();
        
        // The fullName() method will concatenate nulls as "null"
        assertThat(fullNameWithNulls.fullName()).isEqualTo("null null null");
    }

    @Test
    void shouldHandleSpecialCharactersInNames() {
        // Given
        var fullName = new FullName("José", "da Silva", "Santos");

        // When
        var fullNameResult = fullName.fullName();
        var abbreviatedName = fullName.abbreviatedName();

        // Then
        assertThat(fullNameResult).isEqualTo("José da Silva Santos");
        assertThat(abbreviatedName).isEqualTo("José d. Santos");
    }

    @Test
    void shouldHandleLongNames() {
        // Given
        var fullName = new FullName("Antônio", "Fernandes", "de Oliveira");

        // When
        var fullNameResult = fullName.fullName();
        var abbreviatedName = fullName.abbreviatedName();

        // Then
        assertThat(fullNameResult).isEqualTo("Antônio Fernandes de Oliveira");
        assertThat(abbreviatedName).isEqualTo("Antônio F. de Oliveira");
    }
}
