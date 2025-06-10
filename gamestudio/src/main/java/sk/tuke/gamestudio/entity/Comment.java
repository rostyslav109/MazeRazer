package sk.tuke.gamestudio.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@NamedQuery(name = "Comment.getComments", query = "SELECT c FROM Comment c WHERE c.game = :game ORDER BY c.commentedOn DESC")
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ident;

    private String game;

    private String player;

    private String comment;

    private Timestamp commentedOn;

    public Comment() {
    }

    public Comment(String game, String player, String comment, Timestamp commentedOn) {
        this.game = game;
        this.player = player;
        this.comment = comment;
        this.commentedOn = commentedOn;
    }

    public int getIdent() {
        return ident;
    }

    public void setIdent(int ident) {
        this.ident = ident;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getCommentedOn() {
        return commentedOn;
    }

    public void setCommentedOn(Timestamp commentedOn) {
        this.commentedOn = commentedOn;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "ident=" + ident +
                ", game='" + game + '\'' +
                ", player='" + player + '\'' +
                ", comment='" + comment + '\'' +
                ", commentedOn=" + commentedOn +
                '}';
    }
}