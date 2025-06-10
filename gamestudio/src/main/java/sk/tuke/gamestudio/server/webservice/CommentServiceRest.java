package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.CommentException;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentServiceRest {

    @Autowired
    private CommentService commentService;

    // Отримати всі коментарі для гри
    @GetMapping("/{game}")
    public List<Comment> getComments(@PathVariable String game) {
        try {
            return commentService.getComments(game);
        } catch (CommentException e) {
            throw new RuntimeException("Error receiving comments for a game " + game, e);
        }
    }

    // Додати новий коментар
    @PostMapping
    public void addComment(@RequestBody Comment comment) {
        try {
            commentService.addComment(comment);
        } catch (CommentException e) {
            throw new RuntimeException("Error adding a comment", e);
        }
    }
}