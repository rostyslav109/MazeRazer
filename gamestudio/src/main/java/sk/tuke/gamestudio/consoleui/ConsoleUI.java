package sk.tuke.gamestudio.consoleui;

import sk.tuke.gamestudio.core.*;

import java.util.Scanner;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;
import java.util.Date;
import java.sql.Timestamp;

import java.util.List;

public class ConsoleUI {
    private final Scanner scanner;
    private final ScoreService scoreService;
    private final RatingService ratingService;
    private final CommentService commentService;
    private Maze maze;
    private Player player;
    private int stepsCount = 0;  // Лічильник ходів

    public ConsoleUI(ScoreService scoreService, RatingService ratingService, CommentService commentService) {
        this.scanner = new Scanner(System.in);
        this.scoreService = scoreService;
        this.ratingService = ratingService;
        this.commentService = commentService;
    }

    public void start() {
        System.out.println("Top Scores:");
        printTopScores();

        while (true) {
            System.out.println("════════════════════════════════════════");
            System.out.println("🎮 Choose the Level of Difficulty 🎮");
            System.out.println("1️⃣  Easy");
            System.out.println("2️⃣  Medium");
            System.out.println("3️⃣  Hard");
            System.out.println("❌ Press Q to Quit");
            System.out.println("════════════════════════════════════════");


            String input = scanner.next().toUpperCase();

            if (input.equals("Q")) {
                System.out.println("❌ You chose to quit the game. Goodbye!");
                return; // Виходимо з гри
            }

            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("⚠️Invalid input! Please choose a valid level or Q to quit.");
                continue; // Повертаємось на початок циклу, чекаємо правильний ввід
            }

            switch (choice) {
                case 1:
                    maze = Maze.getEasyMaze();
                    break;
                case 2:
                    maze = Maze.getMediumMaze();
                    break;
                case 3:
                    maze = Maze.getHardMaze();
                    break;
                default:
                    System.out.println("⚠️Incorrect selection! The easy level is automatically selected.");
                    maze = Maze.getEasyMaze();
                    break;
            }

            // Якщо ми потрапили сюди, значить вибір правильний
            player = new Player(new Position(1, 1));
            stepsCount = 0; // Обнуляємо перед грою
            gameLoop();
            break; // Виходимо з циклу після вибору рівня
        }
    }
    private void gameLoop() {
        while (true) {
            maze.printMaze(player.getPosition());
            System.out.println("\uD83D\uDD79Enter the direction (WASD) or Q to quit:");

            char moveChar = scanner.next().toUpperCase().charAt(0);

            if (moveChar == 'Q') {
                System.out.println("❌ You quit the game. Your progress was not saved.");
                return;
            }

            Direction direction = null;
            switch (moveChar) {
                case 'W':
                    direction = Direction.UP;
                    break;
                case 'S':
                    direction = Direction.DOWN;
                    break;
                case 'A':
                    direction = Direction.LEFT;
                    break;
                case 'D':
                    direction = Direction.RIGHT;
                    break;
                default:
                    System.out.println("⚠\uFE0FWrong direction! Use only W, A, S, D, or Q to quit.");
                    continue; // Пропускаємо цей крок і чекаємо новий ввід
            }

            if (direction != null) {
                player.move(direction, maze);
                stepsCount++;  // Збільшуємо кількість ходів
                if (maze.isExit(player.getPosition())) {
                    System.out.println("🎉 Congratulations! You have reached the exit! 🎉");
                    handlePostGame();
                    return;
                }
            }
        }
    }

    private void handlePostGame() {
        System.out.println("════════════════════════════════════════");
        System.out.println("💾 Enter your name to save your score:");
        String playerName = scanner.next();

        int points = Math.max(1000 - stepsCount * 10, 100);  // Формула балів

        try {
            scoreService.addScore(new Score(playerName, "maze", points, new Date()));
            System.out.println("\uD83C\uDFC6Your score has been saved! You earned " + points + " points.");
        } catch (Exception e) {
            System.out.println("❌Error saving score: " + e.getMessage());
        }

        System.out.println("\uD83D\uDCAC Would you like to rate the game? (1-5, 0 to skip):");
        int rating = scanner.nextInt();
        if (rating >= 1 && rating <= 5) {
            try {
                ratingService.setRating(new Rating("maze", playerName, rating, "Rated via ConsoleUI", new Date()));
                System.out.println("\uD83D\uDC4D Thank you for your rating!");
            } catch (Exception e) {
                System.out.println("❌Error saving rating: " + e.getMessage());
            }
        }

        System.out.println("Would you like to leave a comment? (y/n):");
        if (scanner.next().equalsIgnoreCase("y")) {
            System.out.println("✍\uFE0F Enter your comment:");
            scanner.nextLine();  // Consume newline
            String comment = scanner.nextLine();
            try {
                commentService.addComment(new Comment(playerName, "maze", comment, new Timestamp(System.currentTimeMillis())));
                System.out.println("\uD83D\uDCDD Thank you for your feedback!");
            } catch (Exception e) {
                System.out.println("❌Error saving comment: " + e.getMessage());
            }
        }
    }

    private void printTopScores() {
        try {
            List<Score> scores = scoreService.getTopScores("Maze Racer");
            for (Score score : scores) {
                System.out.println(score.getPlayer() + " - " + score.getPoints() + " points");
            }
        } catch (Exception e) {
            System.out.println("Error fetching top scores: " + e.getMessage());
        }
    }
}