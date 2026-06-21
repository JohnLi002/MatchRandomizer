package com.example.MatchRandomizer.data.bracket_format;

import com.example.MatchRandomizer.data.entity.Match;

import java.util.List;

public class Round_List {
    private List<Match> Round1;
    private List<Match> Round2;
    private List<Match> Round3;
    private List<Match> Round4;
    private List<Match> Round5;

    public Round_List(List<Match> R1, List<Match> R2){
        Round1 = R1;
        Round2 = R2;
    }

    public List<Match> getRound1() {
        return Round1;
    }

    public void setRound1(List<Match> round1) {
        Round1 = round1;
    }

    public List<Match> getRound2() {
        return Round2;
    }

    public void setRound2(List<Match> round2) {
        Round2 = round2;
    }
}
