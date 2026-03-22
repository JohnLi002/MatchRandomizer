package com.example.MatchRandomizer.service;

import com.example.MatchRandomizer.data.repo.MatchRepo;
import com.example.MatchRandomizer.data.repo.PeopleRepo;
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

}
