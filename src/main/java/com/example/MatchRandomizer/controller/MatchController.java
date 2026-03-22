package com.example.MatchRandomizer.controller;

import com.example.MatchRandomizer.Form;
import com.example.MatchRandomizer.MatchForm;
import com.example.MatchRandomizer.data.entity.Environment;
import com.example.MatchRandomizer.data.entity.Match;
import com.example.MatchRandomizer.data.entity.Person;
import com.example.MatchRandomizer.service.EnvironmentService;
import com.example.MatchRandomizer.service.MatchService;
import com.example.MatchRandomizer.service.PeopleService;
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
public class MatchController implements WebMvcConfigurer {

    @Autowired
    private PeopleService peopleService;


    @Autowired
    private EnvironmentService environmentService;

    @Autowired
    private MatchService matchService;

    Person[] pair;



    @GetMapping(path="/match")
    public String randomizeMatch(Model model){
        if(peopleService.getNumberOfPeople() < 2){ //checks to see if there are enough people
            return "match_error";
        }

        pair = peopleService.getPair();

        model.addAttribute("Opponent1", pair[0]);
        model.addAttribute("Opponent2", pair[1]);
        MatchForm mf = new MatchForm();
        mf.setPlayer1_id(pair[0].getID());
        mf.setPlayer2_id(pair[1].getID());
        model.addAttribute("Victor", new MatchForm());

        return "match_pair";
    }

    @PostMapping(path="/match")
    public String addMatch(@ModelAttribute MatchForm winner, BindingResult bindingResult, Model model){
        matchService.saveDetails(new Match(pair[0],pair[1],winner.getWinner(),winner.getRound()));

        model.addAttribute("form", new Form());

        return "form";
    }

    @GetMapping(path="/all_matches")
    public String getAllMatches(Model model){
        List<Match> match_list = matchService.getAllMatches();

        model.addAttribute("match_list", match_list);
        return "all_matches";
    }

    @GetMapping(path="/delete_match/{id}")
    public String deleteMatch(@PathVariable (value = "id") int ID, Model model){
        Match match_to_be_deleted = matchService.findMatch(ID);

        matchService.deleteMatch(match_to_be_deleted);

        List<Match> match_list = matchService.getAllMatches();

        model.addAttribute("match_list", match_list);
        return "all_matches";
    }
}
