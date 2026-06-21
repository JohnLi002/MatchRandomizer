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

    @Autowired
    private PeopleService peopleService;

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

    @GetMapping(path = "/tournament/{id}")
    public String view_tournament(@PathVariable (value = "id") int ID, Model model) {
        List<Match> list_of_matches = matchService.find_related_tournaments(ID);
        model.addAttribute("match_list", list_of_matches);
        model.addAttribute("tournament_id", ID);
        model.addAttribute("tournament", tournamentService.findTournament(ID));
        model.addAttribute("people_list", peopleService.find_related_tournaments(ID));
        model.addAttribute("bracket", "fragments/1_round");

        return "view_tournament";
    }

    @GetMapping(path = "/tournament/{id}/delete")
    public String delete_tournament(@PathVariable (value = "id") int ID, Model model) {
        Tournament t = tournamentService.findTournament(ID);
        tournamentService.deleteTournament(t);

        List<Tournament> tournament_list = tournamentService.getAllTournaments();
        model.addAttribute("tournament_list", tournament_list);

        return "all_tournaments";
    }

    @GetMapping(path = "/tournament/{id}/edit/add/player")
    public String add_player(@PathVariable (value = "id") int ID, Model model) {
        Tournament t = tournamentService.findTournament(ID);
        int current_player_count = peopleService.get_tournament_player_count(ID);

        if(t.getMax_players() == current_player_count){ //checks if it is possible to add more players
            List<Match> list_of_matches = matchService.find_related_tournaments(ID);
            model.addAttribute("match_list", list_of_matches);
            model.addAttribute("tournament_id", ID);
            model.addAttribute("tournament", t);
            model.addAttribute("people_list", peopleService.find_related_tournaments(ID));

            return "view_tournament_full_error";
        }

        List<Person> people_list = peopleService.getAllPeople();
        model.addAttribute("people_list", people_list);
        model.addAttribute("tournament_name", t.getName());
        model.addAttribute("tournament_id", ID);
        model.addAttribute("Form", new Form());

        return "add_person_to_tournament";
    }

    @PostMapping(path = "/tournament/{id}/edit/add/player")
    public String add_player_submit(@PathVariable (value = "id") int ID, @ModelAttribute Form form, BindingResult bindingResult, Model model) {
        Tournament t = tournamentService.findTournament(ID);
        Person p = peopleService.findPersonByID(form.getId());
        p.setTournament(t);
        peopleService.saveDetails(p);

        List<Match> list_of_matches = matchService.find_related_tournaments(ID);
        model.addAttribute("match_list", list_of_matches);
        model.addAttribute("tournament_id", ID);
        model.addAttribute("tournament", t);
        model.addAttribute("people_list", peopleService.find_related_tournaments(ID));
        model.addAttribute("bracket", "fragments/1_round");

        return "view_tournament";
    }

    @GetMapping(path = "/tournament/{id}/edit/max")
    public String edit_max_players(@PathVariable (value = "id") int ID, Model model) {
        model.addAttribute("tournament_name", tournamentService.findTournament(ID).getName());
        model.addAttribute("tournament_id", ID);
        model.addAttribute("tournament", tournamentService.findTournament(ID));
        model.addAttribute("form", new Form());

        return "edit_tournament_max";
    }

    @PostMapping(path = "/tournament/{id}/edit/max")
    public String edit_max_players_submit(@PathVariable (value = "id") int ID, @ModelAttribute Form form, BindingResult bindingResult, Model model) {
        int current_player_num = peopleService.find_related_tournaments(ID).size();
        if(current_player_num > form.getMax_players()){
            model.addAttribute("tournament", tournamentService.findTournament(ID));
            model.addAttribute("form", new Form());

            return "edit_tournament_max_error";
        }

        Tournament t = tournamentService.findTournament(ID);
        t.setMax_players(form.getMax_players());
        tournamentService.saveDetails(t);

        List<Match> list_of_matches = matchService.find_related_tournaments(ID);
        model.addAttribute("match_list", list_of_matches);
        model.addAttribute("tournament_id", ID);
        model.addAttribute("tournament", tournamentService.findTournament(ID));
        model.addAttribute("people_list", peopleService.find_related_tournaments(ID));
        model.addAttribute("bracket", "fragments/1_round");

        return "view_tournament";
    }

    @GetMapping(path = "/tournament/{id}/player/{player_id}/remove")
    public String remove_player(@PathVariable (value = "id") int ID, @PathVariable (value = "player_id") int player_id, Model model) {
        Person p = peopleService.findPersonByID(player_id);
        p.setTournament(null);
        peopleService.saveDetails(p);

        List<Match> list_of_matches = matchService.find_related_tournaments(ID);
        model.addAttribute("match_list", list_of_matches);
        model.addAttribute("tournament_id", ID);
        model.addAttribute("tournament", tournamentService.findTournament(ID));
        model.addAttribute("people_list", peopleService.find_related_tournaments(ID));
        model.addAttribute("bracket", "fragments/1_round");

        return "view_tournament";
    }



    @GetMapping(path = "/tournament/{id}/round/start")
    public String start_round(@PathVariable (value = "id") int ID, Model model) {
        Tournament tournament = tournamentService.findTournament(ID);
        List<Person> players = peopleService.find_related_tournaments(ID);
        if(matchService.completed_current_round(ID)) {
            matchService.generate_matches(tournament, matchService.get_tournament_round(ID), players);
        }

        List<Match> list_of_matches = matchService.find_related_tournaments(ID);
        model.addAttribute("match_list", list_of_matches);
        model.addAttribute("tournament_id", ID);
        model.addAttribute("tournament", tournament);
        model.addAttribute("people_list", players);
        model.addAttribute("bracket", "fragments/1_round");

        return "view_tournament";
    }

}
