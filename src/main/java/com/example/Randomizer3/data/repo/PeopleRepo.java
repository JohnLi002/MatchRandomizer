package com.example.Randomizer3.data.repo;

import com.example.Randomizer3.data.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeopleRepo extends JpaRepository<Person, Integer> {

}
