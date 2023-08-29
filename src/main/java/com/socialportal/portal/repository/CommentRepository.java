package com.socialportal.portal.repository;

import com.socialportal.portal.model.issues.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "SELECT * FROM issue_comments WHERE issue_id = ?1", nativeQuery = true)
    Optional<List<Comment>> findAllCommentsForIssue(long id);
    @Query(value = "SELECT * FROM issue_comments where id = ?1 AND user_entity_id = ?2", nativeQuery = true)
    Optional<Comment> findCommentByUserIdAndCommentId(long commentId, long userId);
}
