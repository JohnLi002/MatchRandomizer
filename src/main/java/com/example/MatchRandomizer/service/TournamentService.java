package com.example.MatchRandomizer.service;

import com.example.MatchRandomizer.data.entity.Match;
import com.example.MatchRandomizer.data.entity.Tournament;
import com.example.MatchRandomizer.data.repo.MatchRepo;
import com.example.MatchRandomizer.data.repo.TournamentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TournamentService {
    @Autowired
    private TournamentRepo tournamentRepo;

    public List<Tournament> getAllTournaments(){
        List<Tournament> t = tournamentRepo.findAll();

        return t;
    }

    public void saveDetails(Tournament t){
        tournamentRepo.save(t);
    }

    public Tournament findTournament(int id){
        Optional<Tournament> search_result = tournamentRepo.findById(id);

        if(search_result.isPresent()){
            return search_result.get();
        }

        return null;
    }

    public void deleteTournament(Tournament t){
        tournamentRepo.delete(t);
    }

    public int get_max_rounds(Tournament t){
        return (int) (Math.log(t.getMax_players()) / Math.log(2));
    }
}
