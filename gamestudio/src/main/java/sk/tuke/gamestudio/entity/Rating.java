package sk.tuke.gamestudio.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@NamedQuery(name = "Rating.setRating", query = "UPDATE Rating r SET r.rating = :rating WHERE r.game = :game AND r.player = :player")
@NamedQuery(name = "Rating.getAverageRating", query = "SELECT AVG(r.rating) FROM Rating r WHERE r.game = :game")
@NamedQuery(name = "Rating.getRating", query = "SELECT r FROM Rating r WHERE r.game = :game AND r.player = :player")
public class Rating implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ident;

    private String game;

    private String player;

    private int rating;

    private String comments;

    @Temporal(TemporalType.TIMESTAMP)
    private Date ratedOn;

    public Rating() {
    }

    public Rating(String game, String player, int rating, String comments, Date ratedOn) {
        this.game = game;
        this.player = player;
        this.rating = rating;
        this.comments = comments;
        this.ratedOn = ratedOn;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getRatedOn() {
        return ratedOn;
    }

    public void setRatedOn(Date ratedOn) {
        this.ratedOn = ratedOn;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "ident=" + ident +
                ", game='" + game + '\'' +
                ", player='" + player + '\'' +
                ", rating=" + rating +
                ", comments='" + comments + '\'' +
                ", ratedOn=" + ratedOn +
                '}';
    }
}