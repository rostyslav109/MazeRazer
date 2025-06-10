package sk.tuke.gamestudio.service.repository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.repository.ScoreRepository;
import sk.tuke.gamestudio.service.ScoreException;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.List;

@Service
public class ScoreServiceJPA implements ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    @Override
    @Transactional
    public void addScore(Score score) throws ScoreException {
        try {
            scoreRepository.save(score);
        } catch (Exception e) {
            throw new ScoreException("Could not save score", e);
        }
    }

    @Override
    @Transactional
    public List<Score> getTopScores(String game) throws ScoreException {
        try {
            return scoreRepository.findTopScoresByGame(game);
        } catch (Exception e) {
            throw new ScoreException("Could not retrieve top scores", e);
        }
    }

    @Override
    @Transactional
    public void reset() throws ScoreException {
        try {
            scoreRepository.deleteAll();
        } catch (Exception e) {
            throw new ScoreException("Could not reset scores", e);
        }
    }
}