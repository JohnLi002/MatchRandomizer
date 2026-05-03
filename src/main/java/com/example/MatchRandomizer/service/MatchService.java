package com.example.MatchRandomizer.service;

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
            if (list_of_matches.get(i).getTournament_id().getId() != tournament_id) {
                list_of_matches.remove(i);
                i--;
            }
        }

        return list_of_matches;
    }
}
