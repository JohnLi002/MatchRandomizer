package com.example.MatchRandomizer.service;

import com.example.MatchRandomizer.data.repo.PeopleRepo;
import com.example.MatchRandomizer.data.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeopleService {
    @Autowired
    private PeopleRepo peopleRepo;


    public Person saveDetails(Person person){
        return peopleRepo.save(person);
    }

    public List<Person> getAllPeople(){
        return peopleRepo.findAll();
    }

    public Person getRandomPerson(){
        List<Person> p = getAllPeople();
        return p.get((int) (Math.random()*p.size()));
    }

    public Person[] getPair(){
        Person[] pair = new Person[2];
        List<Person> p = getAllPeople();

        int i = (int) (Math.random()*p.size()); //get random integer for first Person
        pair[0] = p.get(i);
        p.remove(i); //remove person from the array list

        i = (int) (Math.random()*p.size());
        pair[1] = p.get(i);
        return pair;
    }

    public int getNumberOfPeople(){
        return peopleRepo.findAll().size();
    }
}
