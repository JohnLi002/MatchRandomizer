package com.example.Randomizer3.controller;

import com.example.Randomizer3.Form;
import com.example.Randomizer3.data.entity.Environment;
import com.example.Randomizer3.service.EnvironmentService;
import com.example.Randomizer3.service.PeopleService;
import com.example.Randomizer3.data.entity.Person;
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
        String peopleString = "";

        for(int num = 0; num < allPeople.size(); num++){
            peopleString+=allPeople.get(num).getName()+"\n";
        }

        model.addAttribute("allPeople", allPeople);

        model.addAttribute("people", peopleString);
        return "all_people";
    }

    @GetMapping(path="/match")
    public String randomizeMatch(Model model){
        if(peopleService.getNumberOfPeople() < 2){ //checks to see if there are enough people
            return "match_error";
        }

        Person[] pair = peopleService.getPair();

        model.addAttribute("Opponent1", pair[0]);
        model.addAttribute("Opponent2", pair[1]);

        return "match_pair";
    }
}
