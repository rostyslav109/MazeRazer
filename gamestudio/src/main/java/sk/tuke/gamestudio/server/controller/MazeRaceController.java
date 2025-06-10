package sk.tuke.gamestudio.server.controller;

import sk.tuke.gamestudio.core.Maze;
import sk.tuke.gamestudio.core.Position;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.core.MazeGenerator;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class MazeRaceController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private ScoreService scoreService;

    private String playerName;

    private Maze maze;
    private Position playerPosition;
    private int steps;
    private int scores;
    private boolean gameFinished;
    private boolean gameOver;
    private boolean mazeLoaded = false;
    private String difficulty = "easy"; // default
//    private long startTime;
//    private long endTime;

    @RequestMapping("/maze")
    public String maze(@RequestParam(value = "command", required = false) String command,
                       @RequestParam(value = "comment", required = false) String comment,
                       @RequestParam(value = "rating", required = false) Integer rating,
                       @RequestParam(value = "playerName", required = false) String playerNameParam,
                       Model model) {

        if (playerNameParam != null && !playerNameParam.trim().isEmpty()) {
            this.playerName = playerNameParam.trim();
        }

        if (comment != null && !comment.isEmpty()) {
            commentService.addComment(new Comment("maze", this.playerName, comment, new Timestamp(new Date().getTime())));
        }

        if (rating != null) {
            ratingService.setRating(new Rating("maze", this.playerName, rating, "", new Date()));
        }

        if (command != null) {
            handleCommand(command);
        }

        model.addAttribute("mazeLoaded", mazeLoaded);

        // Завжди показувати коментарі та рейтинг
        model.addAttribute("averageRating", ratingService.getAverageRating("maze"));
        model.addAttribute("playerRating", ratingService.getRating("maze", this.playerName));
        model.addAttribute("playerName", this.playerName);

        List<Score> scores = scoreService.getTopScores("maze").stream()
                .limit(10)
                .collect(Collectors.toList());
        List<Comment> comments = commentService.getComments("maze");

        Map<String, String> commentMap = comments.stream()
                .collect(Collectors.toMap(Comment::getPlayer, Comment::getComment, (c1, c2) -> c1));

        List<ScoreWithComment> scoreWithComments = scores.stream()
                .map(score -> new ScoreWithComment(
                        score.getPlayer(),
                        score.getPoints(),
                        score.getPlayedOn(),
                        commentMap.getOrDefault(score.getPlayer(), "—")
                ))
                .collect(Collectors.toList());

        model.addAttribute("scoreWithComments", scoreWithComments);



        if (mazeLoaded) {
            model.addAttribute("htmlField", getHtmlField(maze, playerPosition));
            model.addAttribute("steps", steps);
            model.addAttribute("gameFinished", gameFinished);
            model.addAttribute("gameOver", gameOver);
            model.addAttribute("score", calculateScore());
//            model.addAttribute("startTime", startTime);
        }

        return "maze";
    }

    

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("comments", commentService.getComments("maze"));
        model.addAttribute("averageRating", ratingService.getAverageRating("maze"));
        return "home";
    }

    private void handleCommand(String command) {
        switch (command) {
            case "new":
                mazeLoaded = false;
                break;
            case "easy":
                maze = new MazeGenerator(15, 15).generate();
                difficulty = "easy";
                resetGame();
                mazeLoaded = true;
                break;
            case "medium":
                maze = new MazeGenerator(21, 21).generate();
                difficulty = "medium";
                resetGame();
                mazeLoaded = true;
                break;
            case "hard":
                maze = new MazeGenerator(31, 31).generate();
                difficulty = "hard";
                resetGame();
                mazeLoaded = true;
                break;
//            case "restart":
//                if (mazeLoaded && maze != null) {
//                    resetGame();
//                }
//                break;
            case "up":
            case "down":
            case "left":
            case "right":
                if (!gameFinished && !gameOver && playerPosition != null) {
                    movePlayer(command);
                    mazeLoaded = true;
                }
                break;
            case "finish":
                gameOver();
                break;
            case "changeName":
                this.playerName = null;
                mazeLoaded = false;
                break;
        }
    }


    private void resetGame() {
        playerPosition = new Position(1, 1);
        steps = 0;
        gameFinished = false;
        gameOver = false;
//        startTime = System.currentTimeMillis();
//        endTime = 0;
    }


    private void movePlayer(String direction) {
        int newX = playerPosition.getX();
        int newY = playerPosition.getY();

        switch (direction) {
            case "up":
                newY--;
                break;
            case "down":
                newY++;
                break;
            case "left":
                newX--;
                break;
            case "right":
                newX++;
                break;
        }

        if (isValidMove(newX, newY)) {
            playerPosition = new Position(newX, newY);
            steps++;

            if (maze.isExit(playerPosition)) {
                finishGame();
            }
        }
    }

    private int calculateScore() {
        int baseScore;
        int penaltyPerStep;

        switch (difficulty) {
            case "easy":
                baseScore = 2100;
                penaltyPerStep = 10;
                break;
            case "medium":
                baseScore = 2000;
                penaltyPerStep = 8;
                break;
            case "hard":
                baseScore = 3000;
                penaltyPerStep = 6;
                break;
            default:
                baseScore = 1000;
                penaltyPerStep = 10;
        }

        int score = baseScore - steps * penaltyPerStep;
        return Math.max(score, 0);
    }


    private boolean isValidMove(int x, int y) {
        return x >= 0 && y >= 0
                && y < maze.getGrid().length
                && x < maze.getGrid()[y].length
                && maze.getGrid()[y][x] != '#';
    }

    private void finishGame() {
        gameFinished = true;
//        endTime = System.currentTimeMillis();


            int score = calculateScore();
            scoreService.addScore(new Score("maze", this.playerName, score, new Date()));

    }

    private void gameOver() {
        if (!gameOver && !gameFinished) {
            gameOver = true;
//            endTime = System.currentTimeMillis();
            int score = calculateScore();
            scoreService.addScore(new Score("maze", this.playerName, score, new Date()));
        }
    }

    public String getHtmlField(Maze maze, Position playerPosition) {
        StringBuilder sb = new StringBuilder();
        sb.append("<div class='maze-table'>");

        for (int y = 0; y < maze.getGrid().length; y++) {
            sb.append("<div style='display: flex;'>");
            for (int x = 0; x < maze.getGrid()[y].length; x++) {
                char cell = maze.getGrid()[y][x];
                String cellClass = "maze-cell ";

                if (playerPosition.getX() == x && playerPosition.getY() == y) {
                    cellClass += "player";
                } else if (cell == '#') {
                    cellClass += "wall";
                } else if (cell == 'S') {
                    cellClass += "start";
                } else if (cell == 'E') {
                    cellClass += "exit";
                } else {
                    cellClass += "empty";
                }

                sb.append("<div class='" + cellClass + "'>");
                sb.append("&nbsp;");
                sb.append("</div>");
            }
            sb.append("</div>");
        }

        sb.append("</div>");
        return sb.toString();
    }
}
