package sk.tuke.gamestudio.server.controller;

import java.util.Date;

public class ScoreWithComment {
    private String player;
    private int points;
    private Date playedOn;
    private String comment;

    public ScoreWithComment(String player, int points, Date playedOn, String comment) {
        this.player = player;
        this.points = points;
        this.playedOn = playedOn;
        this.comment = comment;
    }

    public String getPlayer() {
        return player;
    }

    public int getPoints() {
        return points;
    }

    public Date getPlayedOn() {
        return playedOn;
    }

    public String getComment() {
        return comment;
    }
}