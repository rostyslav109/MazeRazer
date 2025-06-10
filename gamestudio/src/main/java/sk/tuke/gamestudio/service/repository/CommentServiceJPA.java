package sk.tuke.gamestudio.service.repository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.service.repository.CommentRepository;
import sk.tuke.gamestudio.service.CommentException;
import sk.tuke.gamestudio.service.CommentService;

import java.util.List;

@Service
public class CommentServiceJPA implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    @Transactional
    public void addComment(Comment comment) throws CommentException {
        try {
            commentRepository.save(comment);
        } catch (Exception e) {
            throw new CommentException("Could not save comment", e);
        }
    }

    @Override
    @Transactional
    public List<Comment> getComments(String game) throws CommentException {
        try {
            return commentRepository.getComments(game);
        } catch (Exception e) {
            throw new CommentException("Could not retrieve comments", e);
        }
    }

    @Override
    @Transactional
    public void reset() throws CommentException {
        try {
            commentRepository.deleteAll();
        } catch (Exception e) {
            throw new CommentException("Could not reset comments", e);
        }
    }
}