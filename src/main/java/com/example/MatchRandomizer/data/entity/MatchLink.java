package com.example.MatchRandomizer.data.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "match_links")
public class MatchLink {
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="match1_ID", referencedColumnName="id")
    private Match match1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="match2_ID", referencedColumnName="id")
    private Match match2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="main_match_ID", referencedColumnName="id")
    private Match main_match;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="tournament_id", referencedColumnName="id")
    private Tournament tournament;

    public MatchLink(){

    }

    public MatchLink(Match m1, Match m2, Match main_match){
        setMatch1(m1);
        setMatch2(m2);
        setMain_match(main_match);
    }

    public MatchLink(Match m1, Match m2, Match main_match, Tournament t){
        setMatch1(m1);
        setMatch2(m2);
        setMain_match(main_match);
        setTournament(t);
    }

    public Match getMatch1() {
        return match1;
    }

    public void setMatch1(Match match1) {
        this.match1 = match1;
    }

    public Match getMatch2() {
        return match2;
    }

    public void setMatch2(Match match2) {
        this.match2 = match2;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Match getMain_match() {
        return main_match;
    }

    public void setMain_match(Match main_match) {
        this.main_match = main_match;
    }
}
