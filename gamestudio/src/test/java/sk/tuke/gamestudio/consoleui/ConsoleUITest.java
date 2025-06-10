//import sk.tuke.gamestudio.consoleui.ConsoleUI;
//import sk.tuke.gamestudio.core.Maze;
//import sk.tuke.gamestudio.core.Player;
//import sk.tuke.gamestudio.core.Position;
//import sk.tuke.gamestudio.service.CommentService;
//import sk.tuke.gamestudio.service.RatingService;
//import sk.tuke.gamestudio.service.ScoreService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.PrintStream;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.*;
//
//class ConsoleUITest {
//    private ScoreService scoreService;
//    private RatingService ratingService;
//    private CommentService commentService;
//    private ConsoleUI consoleUI;
//    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//
//    @BeforeEach
//    void setUp() {
//        scoreService = mock(ScoreService.class);
//        ratingService = mock(RatingService.class);
//        commentService = mock(CommentService.class);
//        consoleUI = new ConsoleUI(scoreService, ratingService, commentService);
//        System.setOut(new PrintStream(outContent));
//    }
//
//    @Test
//    void testStartGameWithEasyLevel() {
//        String input = "1\nQ\n";
//        System.setIn(new ByteArrayInputStream(input.getBytes()));
//        consoleUI.start();
//        assertTrue(outContent.toString().contains("Top Scores:"));
//        assertTrue(outContent.toString().contains("Easy"));
//    }
//
//    @Test
//    void testStartGameWithInvalidChoiceDefaultsToEasy() {
//        String input = "5\nQ\n";
//        System.setIn(new ByteArrayInputStream(input.getBytes()));
//        consoleUI.start();
//        assertTrue(outContent.toString().contains("Incorrect selection! The easy level is automatically selected."));
//    }
//
//    @Test
//    void testGameLoopQuit() {
//        String input = "1\nQ\n";
//        System.setIn(new ByteArrayInputStream(input.getBytes()));
//        consoleUI.start();
//        assertTrue(outContent.toString().contains("❌ You quit the game. Your progress was not saved."));
//    }
//
//    @Test
//    void testWinningGame() {
//        // Мокаємо Maze та Player
//        Maze mockMaze = mock(Maze.class);
//        Player mockPlayer = mock(Player.class);
//
//        // Перевизначаємо методи
//        when(mockMaze.isExit(any(Position.class))).thenReturn(true);
//
//        // Підставляємо замість реальних об'єктів мокані
//        consoleUI = new ConsoleUI(scoreService, ratingService, commentService);
//
//        // Створюємо вхід для тесту
//        String input = "1\nD\nD\nD\nD\n"; // Чотири ходи вперед
//        System.setIn(new ByteArrayInputStream(input.getBytes()));
//
//        // Запускаємо гру
//        consoleUI.start();
//
//        // Перевіряємо, чи відображається повідомлення про перемогу
//        assertTrue(outContent.toString().contains("🎉 Congratulations! You have reached the exit! 🎉"));
//    }
//}