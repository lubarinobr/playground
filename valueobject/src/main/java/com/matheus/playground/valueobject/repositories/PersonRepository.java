package com.matheus.playground.valueobject.repositories;

import com.matheus.playground.valueobject.entities.Person;
import com.matheus.playground.valueobject.entities.SocialNumberId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, SocialNumberId> {
}
