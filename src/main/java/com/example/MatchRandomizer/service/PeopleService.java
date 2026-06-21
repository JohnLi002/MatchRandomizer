package com.example.MatchRandomizer.service;

import com.example.MatchRandomizer.data.entity.Match;
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

    public Person findPersonByID(int id){
        List<Person> listOfPeople = getAllPeople();
        Person p = null;
        for(int i = 0; i < listOfPeople.size(); i++){
            p = listOfPeople.get(i);
            if(p.getID() == id){
                break;
            }
        }
        return p;
    }

    public List<Person> getOtherPeople(int id){
        List<Person> list_of_people = getAllPeople();
        Person p = findPersonByID(id);
        if(list_of_people.remove(p)){
            list_of_people.add(0, p);
        }

        return list_of_people;
    }

    public List<Person> getOtherPeople(Person p){
        List<Person> list_of_people = getAllPeople();
        if(list_of_people.remove(p)){
            list_of_people.add(0, p);
        }

        return list_of_people;
    }

    public void deletePerson(Person p){
        peopleRepo.delete(p);
    }

    public List<Person> find_related_tournaments(int tournament_id) {
        List<Person> list_of_people = getAllPeople();

        for (int i = 0; i < list_of_people.size(); i++) {
            if (list_of_people.get(i).getTournament() == null) { //check to make sure that the person has an attached tournament
                list_of_people.remove(i);
                i--;
            } else if (list_of_people.get(i).getTournament().getId() != tournament_id) {
                list_of_people.remove(i);
                i--;
            }
        }

        return list_of_people;
    }

    public int get_tournament_player_count(int tournament_id){
        return find_related_tournaments(tournament_id).size();
    }
}
