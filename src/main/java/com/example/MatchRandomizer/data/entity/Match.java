package com.example.MatchRandomizer.data.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "matches")
public class Match {
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="player1_ID", referencedColumnName="id")
    private Person player1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="player2_ID", referencedColumnName="id")
    private Person player2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="winner_ID", referencedColumnName="id")
    private Person winner;

    @Column(name = "round")
    private int round;

    public Match(Person opp1, Person opp2, Person winner, int i){
        setPlayer1(opp1);
        setPlayer2(opp2);
        setWinner(winner);
        setRound(i);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPlayer1() {
        return player1;
    }

    public void setPlayer1(Person player1) {
        this.player1 = player1;
    }

    public Person getPlayer2() {
        return player2;
    }

    public void setPlayer2(Person player2) {
        this.player2 = player2;
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
