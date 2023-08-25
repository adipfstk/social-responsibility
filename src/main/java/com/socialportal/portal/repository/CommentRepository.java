package com.socialportal.portal.repository;

import com.socialportal.portal.model.issues.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comments, Long> {
    @Query(value = "SELECT * FROM issue_comments WHERE issue_id = ?1", nativeQuery = true)
    List<Comments> findAllCommentsForIssue(long id);
}
