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

    @Autowired
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
        return "redirect:/tournament/all";
    }

    @GetMapping("/tournament/all")
    public String display_all_tournaments(Model model) {
        List<Tournament> list_of_tournaments = tournamentService.getAllTournaments();
        model.addAttribute("tournament_list", list_of_tournaments);

        return "all_tournaments";
    }

    @GetMapping(path = "/tournament/{id}/edit")
    public String edit_tournament(@PathVariable (value = "id") int ID, Model model) {
        List<Match> list_of_matches = matchService.find_related_tournaments(ID);
        model.addAttribute("match_list", list_of_matches);

        return "edit_tournament";
    }

    @GetMapping(path = "/tournament/{id}/delete")
    public String delete_tournament(@PathVariable (value = "id") int ID, Model model) {
        Tournament t = tournamentService.findTournament(ID);
        tournamentService.deleteTournament(t);

        List<Tournament> tournament_list = tournamentService.getAllTournaments();
        model.addAttribute("tournament_list", tournament_list);

        return "all_tournaments";
    }
}
