package com.matheus.playground.valueobject.services;

import com.matheus.playground.valueobject.entities.FullName;
import com.matheus.playground.valueobject.entities.Person;
import com.matheus.playground.valueobject.entities.SocialNumberId;
import com.matheus.playground.valueobject.exceptions.UserEmailException;
import com.matheus.playground.valueobject.repositories.PersonRepository;
import com.matheus.playground.valueobject.valueobjects.SocialNumber;
import com.matheus.playground.valueobject.valueobjects.UserEmail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
class PersonServiceIntegrationTest {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    private Person testPerson;

    private final String socialNumber = "12345678912";

    @BeforeEach
    void setUp() {
        // Clean up database before each test
        personRepository.deleteAll();
        
        // Create test person
        var socialNumberVO = new SocialNumber(this.socialNumber);
        var socialNumberId = SocialNumberId.builder()
                .socialNumber(socialNumberVO)
                .build();
        
        var fullName = new FullName("João", "Silva", "Santos");
        
        testPerson = Person.builder()
                .id(socialNumberId)
                .name(fullName)
                .email(new UserEmail("matheus@xpto.com"))
                .build();
    }

    @Test
    void shouldSavePersonSuccessfully() {
        // When
        personService.savePerson(testPerson);

        // Then
        var savedPerson = personRepository.findById(testPerson.getId()).orElse(null);
        assertThat(savedPerson).isNotNull();
        assertThat(savedPerson.getName().fullName()).isEqualTo("João Silva Santos");
        assertThat(savedPerson.getId().getSocialNumber().value()).isEqualTo(this.socialNumber);
    }

    @Test
    void shouldRetrieveAllPersons() {
        // Given
        personService.savePerson(testPerson);
        
        var anotherSocialNumber = new SocialNumber("22233344456");
        var anotherSocialNumberId = SocialNumberId.builder()
                .socialNumber(anotherSocialNumber)
                .build();
        
        var anotherFullName = new FullName("Maria", "Santos", "Oliveira");
        var anotherPerson = Person.builder()
                .id(anotherSocialNumberId)
                .name(anotherFullName)
                .build();
        
        personService.savePerson(anotherPerson);

        // When
        personService.getAllPersons();

        // Then
        var allPersons = personRepository.findAll();
        assertThat(allPersons).hasSize(2);
        assertThat(allPersons).extracting(person -> person.getName().fullName())
                .containsExactlyInAnyOrder("João Silva Santos", "Maria Santos Oliveira");
    }

    @Test
    void shouldHandleEmptyRepository() {
        // When
        personService.getAllPersons();

        // Then
        var allPersons = personRepository.findAll();
        assertThat(allPersons).isEmpty();
    }

    @Test
    void shouldPersistPersonWithEmbeddedId() {
        // Given
        var socialNumber = new SocialNumber("33344455567");
        var socialNumberId = SocialNumberId.builder()
                .socialNumber(socialNumber)
                .build();
        
        var fullName = new FullName("Pedro", "Oliveira", "Costa");
        var person = Person.builder()
                .id(socialNumberId)
                .name(fullName)
                .build();

        // When
        personService.savePerson(person);

        // Then
        var savedPerson = personRepository.findById(socialNumberId).orElse(null);
        assertThat(savedPerson).isNotNull();
        assertThat(savedPerson.getId().getSocialNumber().value()).isEqualTo("33344455567");
        assertThat(savedPerson.getName().fullName()).isEqualTo("Pedro Oliveira Costa");
    }

    @Test
    void shouldValidateSocialNumberFormat() {
        // Given - Invalid social number (less than 11 digits)
        try {
            var invalidSocialNumber = new SocialNumber("1234567890"); // 10 digits
            var socialNumberId = SocialNumberId.builder()
                    .socialNumber(invalidSocialNumber)
                    .build();
            
            var fullName = new FullName("Invalid", "Person", "Test");
            var person = Person.builder()
                    .id(socialNumberId)
                    .name(fullName)
                    .build();

            // When & Then
            personService.savePerson(person);
            // If we reach here, the test should fail because validation should have thrown an exception
            assertThat(false).as("Should have thrown IllegalArgumentException for invalid social number").isTrue();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Invalid social number");
        }
    }

    @Test
    void shouldUpdateExistingPerson() {
        // Given
        personService.savePerson(testPerson);
        
        // When - Update the person
        var updatedFullName = new FullName("João", "Silva", "Updated");
        var updatedPerson = Person.builder()
                .id(testPerson.getId())
                .name(updatedFullName)
                .build();
        
        personService.savePerson(updatedPerson);

        // Then
        var savedPerson = personRepository.findById(testPerson.getId()).orElse(null);
        assertThat(savedPerson).isNotNull();
        assertThat(savedPerson.getName().fullName()).isEqualTo("João Silva Updated");
        assertThat(savedPerson.getId().getSocialNumber().value()).isEqualTo(this.socialNumber);
    }

    @Test
    void shouldSaveUserWithEmail() {
        //Give
        var fullName = new FullName("Matheus", "Lubarino", "Silva");
        var person = Person.builder()
                .id(new SocialNumberId(new SocialNumber("12345678912")))
                .email(new UserEmail("matheus@gmail.com"))
                .name(fullName)
                .build();


        // Create person
        personService.savePerson(person);

        // Then
        var personResult = personRepository.findById(person.getId()).orElse(null);
        assertThat(personResult).isNotNull();
        assertThat(personResult.getEmail().email()).isEqualTo("matheus@gmail.com");
        assertThat(personResult.getName().fullName()).isEqualTo("Matheus Lubarino Silva");
        assertThat(personResult.getId().getSocialNumber().value()).isEqualTo("12345678912");
    }

    @Test
    void shouldThrowEmailException() {
        //Give
        assertThrows(UserEmailException.class, () -> {
            var fullName = new FullName("Matheus", "Lubarino", "Silva");
            var person = Person.builder()
                    .id(new SocialNumberId(new SocialNumber("12345678912")))
                    .email(new UserEmail("matheus"))
                    .name(fullName)
                    .build();

            // Create person
            personService.savePerson(person);
        });
    }

    @Test
    void shouldReturnEmailDomain() {
        var userEmail = new UserEmail("matheus@gmail.com");
        assertThat(userEmail.domain()).isEqualTo("gmail.com");
    }
}