package sk.tuke.gamestudio.server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameStudioController {

    @GetMapping("/test")
    public String test() {
        return "Server is up and running!";
    }
}