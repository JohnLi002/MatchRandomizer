package com.example.MatchRandomizer.service;

import com.example.MatchRandomizer.data.repo.MatchRepo;
import com.example.MatchRandomizer.data.repo.PeopleRepo;
import com.example.MatchRandomizer.data.entity.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {
    @Autowired
    private MatchRepo peopleRepo;

}
