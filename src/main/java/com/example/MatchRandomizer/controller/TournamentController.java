package com.example.MatchRandomizer.controller;

import com.example.MatchRandomizer.Form;
import com.example.MatchRandomizer.MatchForm;
import com.example.MatchRandomizer.data.entity.Environment;
import com.example.MatchRandomizer.data.entity.Match;
import com.example.MatchRandomizer.data.entity.Tournament;
import com.example.MatchRandomizer.service.EnvironmentService;
import com.example.MatchRandomizer.service.MatchService;
import com.example.MatchRandomizer.service.PeopleService;
import com.example.MatchRandomizer.data.entity.Person;
import com.example.MatchRandomizer.service.TournamentService;
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
public class TournamentController {
    @Autowired
    private TournamentService tournamentService;
    private MatchService matchService;

    @GetMapping("/tournament/create")
    public String create_tournament(Model model) {
        model.addAttribute("form", new Form());
        return "create_tournament";
    }

    @PostMapping("/tournament/create")
    public String create_tournament_submission(@ModelAttribute @Valid Form form, BindingResult bindingResult, Model model) {
        Tournament t = new Tournament(form.getName(),form.getMax_players());
        tournamentService.saveDetails(t);
        return "create_tournament";
    }

    @GetMapping("/tournament/all")
    public String display_all_tournaments(Model model) {
        List<Tournament> list_of_tournaments = tournamentService.getAllTournaments();
        model.addAttribute("tournament_list", list_of_tournaments);

        return "all_tournaments";
    }

}
