package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.RatingException;

@RestController
@RequestMapping("/api/rating")
public class RatingServiceRest {

    @Autowired
    private RatingService ratingService;

    // Отримати середній рейтинг гри
    @GetMapping("/{game}")
    public int getAverageRating(@PathVariable String game) {
        try {
            return ratingService.getAverageRating(game);
        } catch (RatingException e) {
            throw new RuntimeException("Error when getting an average rating for a game " + game, e);
        }
    }

    // Отримати рейтинг конкретного гравця для гри
    @GetMapping("/{game}/{player}")
    public int getRating(@PathVariable String game, @PathVariable String player) {
        try {
            return ratingService.getRating(game, player);
        } catch (RatingException e) {
            throw new RuntimeException("Error when receiving a rating for a player " + player, e);
        }
    }

    // Встановити рейтинг
    @PostMapping
    public void setRating(@RequestBody Rating rating) {
        try {
            ratingService.setRating(rating);
        } catch (RatingException e) {
            throw new RuntimeException("Error when setting the rating", e);
        }
    }
}