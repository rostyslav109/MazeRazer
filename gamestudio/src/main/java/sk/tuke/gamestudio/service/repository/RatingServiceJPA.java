package sk.tuke.gamestudio.service.repository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.repository.RatingRepository;
import sk.tuke.gamestudio.service.RatingException;
import sk.tuke.gamestudio.service.RatingService;

import java.util.Optional;

@Service
public class RatingServiceJPA implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    @Transactional
    public void setRating(Rating rating) throws RatingException {
        try {
            Optional<Rating> existingRating = ratingRepository.getRating(rating.getGame(), rating.getPlayer());

            if (existingRating.isPresent()) {
                // Оновлюємо існуючий рейтинг
                rating.setIdent(existingRating.get().getIdent());  // Використовуємо setIdent() замість setId()
                ratingRepository.save(rating);
            } else {
                // Зберігаємо новий рейтинг
                ratingRepository.save(rating);
            }
        } catch (Exception e) {
            throw new RatingException("Could not save rating", e);
        }
    }

    @Override
    @Transactional
    public int getAverageRating(String game) throws RatingException {
        try {
            return (int)Math.round(ratingRepository.getAverageRating(game).orElse(0.0));
        } catch (Exception e) {
            throw new RatingException("Could not retrieve average rating", e);
        }
    }

    @Override
    @Transactional
    public int getRating(String game, String player) throws RatingException {
        try {
            Optional<Rating> ratingOpt = ratingRepository.getRating(game, player);
            return ratingOpt.map(Rating::getRating).orElse(0);  // Використовуємо map для отримання rating
        } catch (Exception e) {
            throw new RatingException("Could not retrieve rating", e);
        }
    }

    @Override
    @Transactional
    public void reset() throws RatingException {
        try {
            ratingRepository.deleteAll();
        } catch (Exception e) {
            throw new RatingException("Could not reset ratings", e);
        }
    }
}