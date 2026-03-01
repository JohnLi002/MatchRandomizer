package com.example.MatchRandomizer.controller;

import com.example.MatchRandomizer.Form;
import com.example.MatchRandomizer.MatchForm;
import com.example.MatchRandomizer.data.entity.Environment;
import com.example.MatchRandomizer.data.entity.Match;
import com.example.MatchRandomizer.service.EnvironmentService;
import com.example.MatchRandomizer.service.MatchService;
import com.example.MatchRandomizer.service.PeopleService;
import com.example.MatchRandomizer.data.entity.Person;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Controller
public class PeopleController implements WebMvcConfigurer {

    @Autowired
    private PeopleService peopleService;

    @Autowired
    private EnvironmentService environmentService;

    @Autowired
    private MatchService matchService;

    Person[] pair;

    @PostMapping("/addPerson")
    public Person postDetails(@RequestBody Person person){
        return peopleService.saveDetails(person);
    }
//comment
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
    }

    @GetMapping("/form")
    public String formForm(Model model) {
        model.addAttribute("form", new Form());
        return "form";
    }

    @PostMapping("/form")
    public String formSubmit(@ModelAttribute @Valid Form form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) { //checks to make sure the inputting name is valid. Conditions: Not Null + 0< characters
            return "form";
        }

        /*model.addAttribute("person", form);*/
        Person p = new Person();
        p.setName(form.getName());
        peopleService.saveDetails(p);
        return "result";
    }

    @GetMapping("/environment")
    public String environmentForm(Model model) {
        model.addAttribute("form", new Form());
        return "input_environment";
    }

    @PostMapping("/environment")
    public String environmentSubmit(@ModelAttribute @Valid Form form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) { //checks to make sure the inputting name is valid. Conditions: Not Null + 0< characters
            return "/environment";
        }

        /*model.addAttribute("person", form);*/
        Environment e = new Environment();
        e.setArea(form.getName());
        environmentService.saveDetails(e);
        return "result";
    }

    @GetMapping(path="/all")
    public String getAllPeople(Model model){
        List<Person> allPeople = peopleService.getAllPeople();

        model.addAttribute("allPeople", allPeople);
        return "all_people";
    }

    @GetMapping(path="/edit_person/{id}")
    public String editPerson(@PathVariable (value = "id") int ID, Model model){
        Person p = peopleService.findPersonByID(ID);

        Form form = new Form();
        form.setId(ID);
        form.setVictories(p.getVictories());
        form.setLoses(p.getLoses());
        form.setName(p.getName());
        model.addAttribute("form", form);
        return "edit_person";
    }

    @PostMapping("/edit_person")
    public String editPersonSubmit(@ModelAttribute Form form, BindingResult bindingResult, Model model) {
        Person newPerson = peopleService.findPersonByID(form.getId());
        if(!form.getName().isEmpty()){
            newPerson.setName(form.getName());
        }
        if(form.getLoses() != newPerson.getLoses()){
            newPerson.setLoses(form.getLoses());
        }
        if(form.getVictories() != newPerson.getVictories()){
            newPerson.setVictories(form.getVictories());
        }

        peopleService.saveDetails(newPerson);
        List<Person> allPeople = peopleService.getAllPeople();
        model.addAttribute("allPeople", allPeople);
        return "all_people";
    }

    @GetMapping(path="/delete_person/{id}")
    public String deletePerson(@PathVariable (value = "id") int ID, Model model){
        Person p = peopleService.findPersonByID(ID);

        peopleService.deletePerson(p);

        List<Person> allPeople = peopleService.getAllPeople();
        model.addAttribute("allPeople", allPeople);
        return "all_people";
    }


    @GetMapping(path="/match")
    public String randomizeMatch(Model model){
        if(peopleService.getNumberOfPeople() < 2){ //checks to see if there are enough people
            return "match_error";
        }

        pair = peopleService.getPair();

        model.addAttribute("Opponent1", pair[0]);
        model.addAttribute("Opponent2", pair[1]);
        model.addAttribute("Victor", new MatchForm());

        return "match_pair";
    }

    @PostMapping(path="/match")
    public String addMatch(@ModelAttribute MatchForm winner, BindingResult bindingResult, Model model){
        matchService.saveDetails(new Match(pair[0],pair[1],winner.getWinner(),winner.getRound()));

        model.addAttribute("form", new Form());

        return "form";
    }
}
