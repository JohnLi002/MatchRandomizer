package com.example.MatchRandomizer.data.repo;

import com.example.MatchRandomizer.data.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeopleRepo extends JpaRepository<Person, Integer> {

}
