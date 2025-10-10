package com.matheus.playground.valueobject.services;

import com.matheus.playground.valueobject.commands.EmailDomainCommand;
import com.matheus.playground.valueobject.entities.Person;
import com.matheus.playground.valueobject.observables.FraudPrevention;
import com.matheus.playground.valueobject.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final FraudPrevention fraudPrevention;

    @Transactional(readOnly = false)
    public void savePerson(Person person) {
        var emailDomainCommand = new EmailDomainCommand(person.getEmail().domain());
        personRepository.save(person);
        fraudPrevention.publishEvent(emailDomainCommand);
    }

    public void getAllPersons() {
        List<Person> all = personRepository.findAll();
        log.info(all.toString());
    }
}
