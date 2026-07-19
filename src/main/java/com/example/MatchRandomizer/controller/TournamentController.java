package com.example.MatchRandomizer.controller;

import com.example.MatchRandomizer.Form;
import com.example.MatchRandomizer.data.entity.*;
import com.example.MatchRandomizer.service.MatchService;
import com.example.MatchRandomizer.service.PeopleService;
import com.example.MatchRandomizer.service.TournamentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        //List<Match> list_of_matches = matchService.find_related_tournaments(ID);
        List<MatchDisplay> list_of_matches = matchService.get_organized_tournament_match_display(ID);

        model.addAttribute("match_list", list_of_matches);
        model.addAttribute("tournament_id", ID);
        model.addAttribute("tournament", tournamentService.findTournament(ID));
        model.addAttribute("people_list", peopleService.find_related_tournaments(ID));
        model.addAttribute("bracket", "fragments/1_round");

        return "view_tournament_test";
    }

    @GetMapping(path = "/tournament/{id}/delete")
    public String delete_tournament(@PathVariable (value = "id") int ID, Model model) {
        Tournament t = tournamentService.findTournament(ID);
        matchService.delete_tournament_matches(t.getId());
        peopleService.remove_tournament(t.getId());
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

        //List<Match> list_of_matches = matchService.find_related_tournaments(ID);
        List<MatchDisplay> list_of_matches = matchService.get_organized_tournament_match_display(ID);

        model.addAttribute("match_list", list_of_matches);
        model.addAttribute("tournament_id", ID);
        model.addAttribute("tournament", t);
        model.addAttribute("people_list", peopleService.find_related_tournaments(ID));
        model.addAttribute("bracket", "fragments/1_round");

        return "view_tournament_test";
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

        //List<Match> list_of_matches = matchService.find_related_tournaments(ID);
        List<MatchDisplay> list_of_matches = matchService.get_organized_tournament_match_display(ID);

        model.addAttribute("match_list", list_of_matches);
        model.addAttribute("tournament_id", ID);
        model.addAttribute("tournament", tournamentService.findTournament(ID));
        model.addAttribute("people_list", peopleService.find_related_tournaments(ID));
        model.addAttribute("bracket", "fragments/1_round");

        return "view_tournament_test";
    }

    @GetMapping(path = "/tournament/{id}/player/{player_id}/remove")
    public String remove_player(@PathVariable (value = "id") int ID, @PathVariable (value = "player_id") int player_id, Model model) {
        Person p = peopleService.findPersonByID(player_id);
        p.setTournament(null);
        peopleService.saveDetails(p);

        //List<Match> list_of_matches = matchService.find_related_tournaments(ID);
        List<MatchDisplay> list_of_matches = matchService.get_organized_tournament_match_display(ID);
        model.addAttribute("match_list", list_of_matches);
        model.addAttribute("tournament_id", ID);
        model.addAttribute("tournament", tournamentService.findTournament(ID));
        model.addAttribute("people_list", peopleService.find_related_tournaments(ID));
        model.addAttribute("bracket", "fragments/1_round");

        return "view_tournament_test";
    }



    @GetMapping(path = "/tournament/{id}/round/start")
    public String start_round(@PathVariable (value = "id") int ID, Model model) {
        Tournament tournament = tournamentService.findTournament(ID);
        List<Person> players = peopleService.find_related_tournaments(ID);

        int rounds = tournamentService.get_max_rounds(tournament);
        matchService.generate_all_matches(tournament,rounds,players);

        //List<Match> list_of_matches = matchService.find_related_tournaments(ID);
        List<MatchDisplay> list_of_matches = matchService.get_organized_tournament_match_display(ID);
        model.addAttribute("match_list", list_of_matches);
        model.addAttribute("tournament_id", ID);
        model.addAttribute("tournament", tournament);
        model.addAttribute("people_list", players);
        //model.addAttribute("bracket", "fragments/1_round");

        return "view_tournament_test";
    }


    @GetMapping(path = "/tournament/{id}/match/{match_id}/winner/{winner_id}")
    public String select_winner(@PathVariable (value = "id") int tournament_id, @PathVariable (value = "match_id") int match_id, @PathVariable (value = "winner_id") int winner_id,Model model) {
        Tournament tournament = tournamentService.findTournament(tournament_id);

        Match m = matchService.findMatch(match_id);
        Person winner = peopleService.findPersonByID(winner_id);
        m.setWinner(winner);
        matchService.saveDetails(m);

        MatchLink ml = matchService.find_related_match(match_id);
        if(ml == null){ //catch null to prevent error

        } else if(ml.getMatch1().getId() == match_id){
            ml.getMain_match().setPlayer1(winner);
            matchService.saveLink(ml);
        } else {
            ml.getMain_match().setPlayer2(winner);
            matchService.saveLink(ml);
        }

        //List<Match> list_of_matches = matchService.find_related_tournaments(tournament_id);
        List<MatchDisplay> list_of_matches = matchService.get_organized_tournament_match_display(tournament_id);

        List<Person> players = peopleService.find_related_tournaments(tournament_id);
        model.addAttribute("match_list", list_of_matches);
        model.addAttribute("tournament_id", tournament_id);
        model.addAttribute("tournament", tournament);
        model.addAttribute("people_list", players);
        //model.addAttribute("bracket", "fragments/1_round");

        return "view_tournament_test";
    }

}
