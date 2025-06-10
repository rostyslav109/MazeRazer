//package sk.tuke.gamestudio.service;
//
//import sk.tuke.gamestudio.entity.Comment;
//import org.junit.jupiter.api.*;
//import java.sql.*;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//public class CommentServiceJDBCTest {
//    private CommentServiceJDBC commentService;
//    private static final String URL = "jdbc:postgresql://localhost/gamestudio";
//    private static final String USER = "postgres";
//    private static final String PASSWORD = "postgres";
//
//    @BeforeEach
//    void setUp() throws SQLException {
//        commentService = new CommentServiceJDBC();
//        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
//             Statement statement = connection.createStatement()) {
//            statement.executeUpdate("DELETE FROM comment");
//        }
//    }
//
//    @Test
//    void testAddAndGetComments() {
//        Comment comment1 = new Comment("MazeRacer", "Player1", "Great game!", new Timestamp(System.currentTimeMillis()));
//        Comment comment2 = new Comment("MazeRacer", "Player2", "Could be better.", new Timestamp(System.currentTimeMillis()));
//        commentService.addComment(comment1);
//        commentService.addComment(comment2);
//
//        List<Comment> comments = commentService.getComments("MazeRacer");
//        assertEquals(2, comments.size());
//        assertEquals("Player1", comments.get(0).getPlayer());
//    }
//
//    @Test
//    void testResetComments() {
//        commentService.addComment(new Comment("MazeRacer", "Player1", "Awesome!", new Timestamp(System.currentTimeMillis())));
//        commentService.reset();
//        List<Comment> comments = commentService.getComments("MazeRacer");
//        assertTrue(comments.isEmpty());
//    }
//}
