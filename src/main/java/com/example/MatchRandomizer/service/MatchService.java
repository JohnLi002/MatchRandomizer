package com.example.MatchRandomizer.service;

import com.example.MatchRandomizer.data.entity.*;
import com.example.MatchRandomizer.data.repo.MatchLinkRepo;
import com.example.MatchRandomizer.data.repo.MatchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MatchService {
    @Autowired
    private MatchRepo matchRepo;

    @Autowired
    private MatchLinkRepo linkRepo;


    public List<Match> getAllMatches(){
        List<Match> m = matchRepo.findAll();

        return m;
    }

    public List<MatchLink> getAllMatchLinks(){
        List<MatchLink> ml = linkRepo.findAll();

        return ml;
    }

    public void saveDetails(Match m){
        matchRepo.save(m);
    }

    public void saveLink(MatchLink ml){
        linkRepo.save(ml);
    }

    public Match findMatch(int id){
        Optional<Match> search_result = matchRepo.findById(id);

        if(search_result.isPresent()){
            return search_result.get();
        }

        return null;
    }

    public void deleteMatch(Match m){
        delete_related_match_links(m);
        matchRepo.delete(m);
    }

    public void delete_Related_Matches(int person_id){
        List<Match> list_of_matches = getAllMatches();

        for(int i = 0; i < list_of_matches.size(); i++) {
            if (list_of_matches.get(i).getPlayer1ID() == person_id) {
                deleteMatch(list_of_matches.get(i));
            } else if (list_of_matches.get(i).getPlayer2ID() == person_id) {
                deleteMatch(list_of_matches.get(i));
            }
        }
    }

    public void delete_tournament_matches(int tournament_id){
        List<Match> list_of_matches = getAllMatches();

        for(int i = 0; i < list_of_matches.size(); i++) {
            if (list_of_matches.get(i).getTournament().getId() == tournament_id) {
                delete_related_match_links(list_of_matches.get(i));
                deleteMatch(list_of_matches.get(i));
            }
        }
    }

    public List<Match> find_related_tournaments(int tournament_id){
        List<Match> list_of_matches = getAllMatches();

        for(int i = 0; i < list_of_matches.size(); i++) {
            if(list_of_matches.get(i).getTournament() == null) { //check to make sure that the match has an attached tournament
                list_of_matches.remove(i);
                i--;
            } else if (list_of_matches.get(i).getTournament().getId() != tournament_id) {
                list_of_matches.remove(i);
                i--;
            }
        }

        return list_of_matches;
    }

    public int get_tournament_round(int tournament_id){
        List<Match> list_of_matches = find_related_tournaments(tournament_id);

        int highest_round = 0;
        int current_round;

        for(int i = 0; i < list_of_matches.size(); i++) {
            current_round = list_of_matches.get(i).getRound();
            if(highest_round < current_round) { //check to make sure that the match has an attached tournament
                highest_round = current_round;
            }
        }
        return highest_round;
    }

    public boolean completed_current_round(int tournament_id){
        List<Match> list_of_matches = getAllMatches();

        for(int i = 0; i < list_of_matches.size(); i++){
            if(!list_of_matches.get(i).hasWinner()){
                return false;
            }
        }
        return true;
    }

    public int get_tournamnent_match_size(int tournament_id){
        return find_related_tournaments(tournament_id).size();
    }

    public void generate_matches(Tournament t, int round, List<Person> players){
        Person player1;
        Person player2;
        int random_num;

        while(!players.isEmpty()){
            random_num  = (int) (Math.random()*players.size());
            player1 = players.get(random_num);
            players.remove(player1);

            random_num  = (int) (Math.random()*players.size());
            player2 = players.get(random_num);
            players.remove(player2);

            Match m = new Match(player1, player2, round, t);
            saveDetails(m);
        }
    }

    public void generate_all_matches(Tournament t, int max_round, List<Person> players){
        Person player1;
        Person player2;
        int random_num;

        List<Match> generated_matches = new ArrayList<>();

        while(!players.isEmpty()){
            random_num  = (int) (Math.random()*players.size());
            player1 = players.get(random_num);
            players.remove(player1);

            random_num  = (int) (Math.random()*players.size());
            player2 = players.get(random_num);
            players.remove(player2);

            Match m = new Match(player1, player2, 1, t);
            saveDetails(m);
            generated_matches.add(m);
        }

        int j = 0;
        int limit = generated_matches.size();
        for(int i = 1; i < max_round; i++){

            for(; j < limit; j+=2){
                Match m = new Match(i+1, t);
                saveDetails(m);
                generated_matches.add(m);

                MatchLink ml = new MatchLink(generated_matches.get(j), generated_matches.get(j+1),m, t);
                saveLink(ml);
            }
            limit = generated_matches.size();
        }
    }

    public List<MatchLink> get_related_match_links(Tournament t){
        List<MatchLink> matchLinks_list = linkRepo.findAll();

        for(int i = 0; i < matchLinks_list.size(); i++) {
            if(matchLinks_list.get(i).getTournament() == null) { //check to make sure that the match has an attached tournament
                matchLinks_list.remove(i);
                i--;
            } else if (matchLinks_list.get(i).getTournament().getId() != t.getId()) {
                matchLinks_list.remove(i);
                i--;
            }
        }

        return matchLinks_list;

    }

    public void delete_related_match_links(Match m){
        List<MatchLink> matchLinks_list = getAllMatchLinks();

        for(int i = 0; i < matchLinks_list.size(); i++) {
            if (matchLinks_list.get(i).getMatch1().getId() == m.getId()
                    || matchLinks_list.get(i).getMatch2().getId() == m.getId()
                    || matchLinks_list.get(i).getMain_match().getId() == m.getId()) {
                delete_match_link(matchLinks_list.get(i));
            }
        }
    }

    public void delete_match_link(MatchLink ml){
        linkRepo.delete(ml);
    }

    public MatchLink find_related_match(int match_id){
        List<MatchLink> matchLink_list = linkRepo.findAll();

        for(int i = 0; i < matchLink_list.size(); i++) {
            if(matchLink_list.get(i).getMatch1().getId() == match_id || matchLink_list.get(i).getMatch2().getId() == match_id) {
                return matchLink_list.get(i);
            }
        }

        return null;
    }

    public MatchLink find_related_match_by_main(int match_id){
        List<MatchLink> matchLink_list = linkRepo.findAll();

        for(int i = 0; i < matchLink_list.size(); i++) {
            if(matchLink_list.get(i).getMain_match().getId() == match_id) {
                return matchLink_list.get(i);
            }
        }

        return null;

    }

    public MatchDisplay convertToDisplay(Match m){
        if(m.getPlayer1() == null && m.getPlayer2() == null) {
            MatchLink ml = find_related_match_by_main(m.getId());
            return new MatchDisplay("Winner of Match "+ml.getMatch1().getId(),"Winner of Match "+ml.getMatch2().getId(),m);
        } else if(m.getPlayer1() != null && m.getPlayer2() == null){
            MatchLink ml = find_related_match_by_main(m.getId());
            return new MatchDisplay(m.getPlayer1(),"Winner of Match "+ml.getMatch2().getId(),m);
        } else if(m.getPlayer1() == null && m.getPlayer2() != null){
            MatchLink ml = find_related_match_by_main(m.getId());
            return new MatchDisplay("Winner of Match "+ml.getMatch1().getId(),m.getPlayer2(),m);
        } else if(m.getPlayer1() != null && m.getPlayer2() != null && m.getWinner() == null){
            MatchLink ml = find_related_match_by_main(m.getId());
            return new MatchDisplay(m.getPlayer1(),m.getPlayer2(),m);
        }
        return new MatchDisplay(m);
    }

    public List<MatchDisplay> convertListToDisplay(List<Match> ml){
        List<MatchDisplay> md = new ArrayList<>();

        for(int i = 0; i <  ml.size(); i++){
            md.add(convertToDisplay(ml.get(i)));
        }

        return md;
    }

    public List<MatchDisplay> getAllMatchDisplay(){
        List<MatchDisplay> md = new ArrayList<>();
        List<Match> list_of_matches = getAllMatches();

        for(int i = 0; i <  list_of_matches.size(); i++){
            md.add(convertToDisplay(list_of_matches.get(i)));
        }

        return md;
    }

    public List<MatchDisplay> get_all_organized_match_display(){
        List<MatchDisplay> md = new ArrayList<>();
        List<Match> list_of_matches = organize_by_id(getAllMatches());

        for(int i = 0; i <  list_of_matches.size(); i++){
            md.add(convertToDisplay(list_of_matches.get(i)));
        }

        return md;
    }

    public List<MatchDisplay> get_organized_tournament_match_display(int tournament_id) {
        List<Match> list_of_matches = organize_by_id(find_related_tournaments(tournament_id));
        List<MatchDisplay> md = convertListToDisplay(list_of_matches);

        return md;
    }


    public List<Match> organize_by_round(List<Match> ml, int rounds){
        List<Match> organized_list = new ArrayList<>();

        for(int i = 1; i <= rounds; i++){
            for(int j = 0; j < ml.size(); j++){
                if(ml.get(j).getRound() == i){
                    organized_list.add(ml.get(j));
                }
            }
        }
        return organized_list;
    }

    public List<Match> organize_by_id(List<Match> ml){
        int i, j;
        boolean swapped = false;
        for(i = 0; i < ml.size() -1 ; i++){
            for(j = 0; j < ml.size() - i - 1; j++){
                if(ml.get(j).getId() > ml.get(j+1).getId()){
                    Collections.swap(ml, j, j+1);
                    swapped = true;
                }
            }
            if(!swapped){
                break;
            }
            swapped = false;

        }
        return ml;
    }
}
