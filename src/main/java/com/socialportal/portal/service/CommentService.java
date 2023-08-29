package com.socialportal.portal.service;

import com.socialportal.portal.dto.CommentDto;
import com.socialportal.portal.model.issues.Comment;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

public interface CommentService {
    Page<CommentDto> getComments (Long issueId, int pageNo, int itemsPerPage);
    CommentDto addComment(Authentication authentication, Comment comment, long issueId);
    void deleteComment(Authentication authentication, long commentId);
}
