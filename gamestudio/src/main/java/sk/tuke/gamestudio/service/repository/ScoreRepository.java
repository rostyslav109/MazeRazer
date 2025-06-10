package sk.tuke.gamestudio.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sk.tuke.gamestudio.entity.Score;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Integer> {
    @Query("SELECT s FROM Score s WHERE s.game = :game ORDER BY s.points DESC")
    List<Score> findTopScoresByGame(String game);
}