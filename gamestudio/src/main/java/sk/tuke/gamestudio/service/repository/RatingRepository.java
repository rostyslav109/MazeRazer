package sk.tuke.gamestudio.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sk.tuke.gamestudio.entity.Rating;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    @Query("SELECT AVG(r.rating) FROM Rating r WHERE r.game = :game")
    Optional<Double> getAverageRating(String game);

    @Query("SELECT r FROM Rating r WHERE r.game = :game AND r.player = :player")
    Optional<Rating> getRating(String game, String player);
}