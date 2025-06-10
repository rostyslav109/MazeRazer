//package sk.tuke.gamestudio.service;
//
//import sk.tuke.gamestudio.entity.Score;
//import org.junit.jupiter.api.*;
//
//import java.sql.*;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//public class ScoreServiceJDBCTest {
//    private ScoreServiceJDBC scoreService;
//    private static final String URL = "jdbc:postgresql://localhost/gamestudio";
//    private static final String USER = "postgres";
//    private static final String PASSWORD = "postgres";
//
//    @BeforeAll
//    void setUpDatabase() throws SQLException {
//        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
//             Statement statement = connection.createStatement()) {
//            statement.executeUpdate("CREATE TABLE IF NOT EXISTS score (game VARCHAR(64), player VARCHAR(64), points INT, played_on TIMESTAMP)");
//        }
//    }
//
//    @BeforeEach
//    void setUp() throws SQLException {
//        scoreService = new ScoreServiceJDBC();
//        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
//             Statement statement = connection.createStatement()) {
//            statement.executeUpdate("DELETE FROM score");
//        }
//    }
//
//    @Test
//    void testAddScore() {
//        Score score = new Score("MazeRacer", "Player1", 100, new Timestamp(System.currentTimeMillis()));
//        scoreService.addScore(score);
//
//        List<Score> scores = scoreService.getTopScores("MazeRacer");
//        assertEquals(1, scores.size());
//        assertEquals("Player1", scores.get(0).getPlayer());
//    }
//
//    @Test
//    void testGetTopScores() {
//        scoreService.addScore(new Score("MazeRacer", "Player1", 100, new Timestamp(System.currentTimeMillis())));
//        scoreService.addScore(new Score("MazeRacer", "Player2", 200, new Timestamp(System.currentTimeMillis())));
//        scoreService.addScore(new Score("MazeRacer", "Player3", 150, new Timestamp(System.currentTimeMillis())));
//
//        List<Score> scores = scoreService.getTopScores("MazeRacer");
//        assertEquals(3, scores.size());
//        assertEquals("Player2", scores.get(0).getPlayer()); // Найбільший бал
//    }
//
//    @Test
//    void testReset() {
//        scoreService.addScore(new Score("MazeRacer", "Player1", 100, new Timestamp(System.currentTimeMillis())));
//        scoreService.reset();
//
//        List<Score> scores = scoreService.getTopScores("MazeRacer");
//        assertTrue(scores.isEmpty());
//    }
//}
