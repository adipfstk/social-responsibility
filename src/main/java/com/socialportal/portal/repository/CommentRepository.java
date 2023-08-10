package com.socialportal.portal.repository;

import com.socialportal.portal.model.issues.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comments, Long> {
}
