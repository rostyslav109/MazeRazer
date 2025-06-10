package sk.tuke.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.consoleui.ConsoleUI;
import sk.tuke.gamestudio.service.*;

@SpringBootApplication
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.REGEX,
                pattern = "sk\\.tuke\\.gamestudio\\.server\\..*"
        )
)
public class SpringClient {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringClient.class).web(WebApplicationType.NONE).run(args);
    }

    @Bean
    public CommandLineRunner runner(ScoreService scoreService, RatingService ratingService, CommentService commentService) {
        return args -> {
            ConsoleUI game = new ConsoleUI(scoreService, ratingService, commentService);
            game.start();
        };
    }

    @Bean
    public ScoreService scoreService() {
        return new ScoreServiceRestClient();
    }

    @Bean
    public RatingService ratingService() {
        return new RatingServiceRestClient();
    }

    @Bean
    public CommentService commentService() {
        return new CommentServiceRestClient();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}


























//controller
//long currentTime = (gameFinished || gameOver) ? endTime : System.currentTimeMillis();
//long elapsedSeconds = (currentTime - startTime) / 1000;
//model.addAttribute("timeElapsed", elapsedSeconds);

//html
//  <script th:inline="javascript">
//let gameFinished = [[${gameFinished}]];
//let gameOver = [[${gameOver}]];
//let startTime = [[${startTime}]];
//let timerInterval;
//
//function updateTimer() {
//    if (gameFinished || gameOver) {
//        if (timerInterval) {
//            clearInterval(timerInterval);
//            timerInterval = null;
//        }
//        return;
//    }
//
//      const now = Date.now();
//      const elapsed = Math.floor((now - startTime) / 1000);
//      const timerElement = document.getElementById("timer");
//    if (timerElement) {
//        timerElement.innerText = elapsed;
//    }
//}
//
//    if (!gameFinished && !gameOver && startTime) {
//updateTimer(); // Update immediately on page load
//timerInterval = setInterval(updateTimer, 1000);
//    }
//
//            window.addEventListener('beforeunload', function() {
//    if (timerInterval) {
//        clearInterval(timerInterval);
//    }
//});
//  </script>
//
//  <p>Time: <span th:text="${timeElapsed}">0</span> seconds</p>
//  <p>Time: <span id="timer">0</span> seconds</p>