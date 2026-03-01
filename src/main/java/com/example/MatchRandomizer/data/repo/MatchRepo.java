package com.example.MatchRandomizer.data.repo;

import com.example.MatchRandomizer.data.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepo extends JpaRepository<Match, Integer> {

}