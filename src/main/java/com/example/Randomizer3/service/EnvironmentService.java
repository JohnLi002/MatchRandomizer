package com.example.Randomizer3.service;

import com.example.Randomizer3.data.entity.Environment;
import com.example.Randomizer3.data.repo.EnvironmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnvironmentService {
    @Autowired
    private EnvironmentRepo environmentRepo;

    public Environment saveDetails(Environment e){
        return environmentRepo.save(e);
    }

    public List<Environment> getEnvironment(){
        return environmentRepo.findAll();
    }

    public Environment getRandomEnvironment(){
        List<Environment> e = getEnvironment();

        return e.get((int) (Math.random()*e.size()));
    }
}
