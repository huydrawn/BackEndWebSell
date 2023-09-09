package spring.server.commercial.repository.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import spring.server.commercial.model.comment.Comment;

@Component
public interface CommentRepository extends JpaRepository<Comment, Integer> {
	  
}
