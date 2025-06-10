//package sk.tuke.gamestudio.service;
//
//import sk.tuke.gamestudio.entity.Rating;
//import org.junit.jupiter.api.*;
//import java.sql.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//public class RatingServiceJDBCTest {
//    private RatingServiceJDBC ratingService;
//    private static final String URL = "jdbc:postgresql://localhost/gamestudio";
//    private static final String USER = "postgres";
//    private static final String PASSWORD = "postgres";
//
//    @BeforeEach
//    void setUp() throws SQLException {
//        ratingService = new RatingServiceJDBC();
//        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
//             Statement statement = connection.createStatement()) {
//            statement.executeUpdate("DELETE FROM rating");
//        }
//    }
//
//    @Test
//    void testSetAndGetRating() {
//        ratingService.setRating(new Rating("MazeRacer", "Player1", 5, new Timestamp(System.currentTimeMillis()).toString()));
//        int rating = ratingService.getRating("MazeRacer", "Player1");
//        assertEquals(5, rating);
//    }
//
//    @Test
//    void testGetAverageRating() {
//        ratingService.setRating(new Rating("MazeRacer", "Player1", 5, new Timestamp(System.currentTimeMillis()).toString()));
//        ratingService.setRating(new Rating("MazeRacer", "Player2", 3, new Timestamp(System.currentTimeMillis()).toString()));
//        int avgRating = ratingService.getAverageRating("MazeRacer");
//        assertEquals(4, avgRating);
//    }
//
//
//    @Test
//    void testResetRating() {
//        ratingService.setRating(new Rating("MazeRacer", "Player1", 4, new Timestamp(System.currentTimeMillis()).toString()));
//        ratingService.reset();
//        assertEquals(0, ratingService.getAverageRating("MazeRacer"));
//    }
//}
