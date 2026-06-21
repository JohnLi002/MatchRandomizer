package com.example.MatchRandomizer.service;

import com.example.MatchRandomizer.data.entity.Person;
import com.example.MatchRandomizer.data.entity.Tournament;
import com.example.MatchRandomizer.data.repo.MatchRepo;
import com.example.MatchRandomizer.data.entity.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatchService {
    @Autowired
    private MatchRepo matchRepo;

    public List<Match> getAllMatches(){
        List<Match> m = matchRepo.findAll();

        return m;
    }

    public void saveDetails(Match m){
        matchRepo.save(m);
    }

    public Match findMatch(int id){
        Optional<Match> search_result = matchRepo.findById(id);

        if(search_result.isPresent()){
            return search_result.get();
        }

        return null;
    }

    public void deleteMatch(Match m){
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
        List<Match> list_of_matches = getAllMatches();

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
}
