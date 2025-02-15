package drlugha.user_app.service;

import drlugha.user_app.entity.Comment;
import drlugha.user_app.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment saveComment(Comment comment) {
        // Implement the logic to save the comment in the repository
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByArticleId(Long articleId) {
        // Implement the logic to fetch comments by article ID from the repository
        return commentRepository.findByArticleId(articleId);
    }
}
