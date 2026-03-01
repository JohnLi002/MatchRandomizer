package com.example.MatchRandomizer;

import com.example.MatchRandomizer.data.entity.Person;

public class MatchForm {
    private int player1_id;
    private int player2_id;
    private Person winner;
    private int round;

    public MatchForm(){
        round = 0;
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
}
