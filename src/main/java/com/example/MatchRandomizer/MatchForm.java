package com.example.MatchRandomizer;

import com.example.MatchRandomizer.data.entity.Match;
import com.example.MatchRandomizer.data.entity.Person;
import com.example.MatchRandomizer.service.MatchService;

public class MatchForm {
    private int id;
    private int player1_id;
    private int player2_id;
    private Person winner;
    private int round;

    public MatchForm(){
        round = 0;
    }

    public MatchForm(Match m){
        player1_id = m.getPlayer1ID();
        player2_id = m.getPlayer2ID();
        winner = m.getWinner();
        round = m.getRound();
        id = m.getId();
    }

    public int getPlayer1_id() {
        return player1_id;
    }

    public void setPlayer1_id(int player1_id) {
        this.player1_id = player1_id;
    }

    public int getPlayer2_id() {
        return player2_id;
    }

    public void setPlayer2_id(int player2_id) {
        this.player2_id = player2_id;
    }

    public Person getWinner() {
        return winner;
    }

    public void setWinner(Person winner) {
        this.winner = winner;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
