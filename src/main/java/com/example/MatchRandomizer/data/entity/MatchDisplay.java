package com.example.MatchRandomizer.data.entity;

public class MatchDisplay {
    private String player1;
    private int player1_id;
    private String player2;
    private int player2_id;
    private String winner;
    private int winner_id;
    private int match_id;
    private int round;

    public MatchDisplay(Match m){
        player1 = m.getPlayer1().getName();
        player1_id = m.getPlayer1ID();

        player2 = m.getPlayer2().getName();
        player2_id = m.getPlayer2ID();

        winner = m.getWinner().getName();
        winner_id = m.getWinnerID();

        match_id = m.getId();
        round = m.getRound();
    }

    public MatchDisplay(Person p1, Person p2, Match m){
        round = m.getRound();

        player1 = p1.getName();
        player1_id = p1.getID();

        player2 = p2.getName();
        player2_id = p2.getID();

        match_id = m.getId();
    }

    public MatchDisplay(String p1, String p2, Match m){
        player1 = p1;
        player1_id = -1;

        player2 = p2;
        player2_id = -1;

        match_id = m.getId();
        round = m.getRound();
    }

    public MatchDisplay(String p1, Person p2, Match m){
        player1 = p1;
        player1_id = -1;

        player2 = p2.getName();
        player2_id = p2.getID();

        match_id = m.getId();
        round = m.getRound();
    }

    public MatchDisplay(Person p1, String p2, Match m){
        player1 = p1.getName();
        player1_id = p1.getID();

        player2 = p2;
        player2_id = -1;

        match_id = m.getId();
        round = m.getRound();
    }


    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public int getPlayer1_id() {
        return player1_id;
    }

    public void setPlayer1_id(int player1_id) {
        this.player1_id = player1_id;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public int getPlayer2_id() {
        return player2_id;
    }

    public void setPlayer2_id(int player2_id) {
        this.player2_id = player2_id;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public int getWinner_id() {
        return winner_id;
    }

    public void setWinner_id(int winner_id) {
        this.winner_id = winner_id;
    }

    public int getMatch_id() {
        return match_id;
    }

    public void setMatch_id(int match_id) {
        this.match_id = match_id;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }
}
