package com.socialportal.portal.service.impl;

import com.socialportal.portal.dto.CommentDto;
import com.socialportal.portal.exception.issue.NoCommentFoundException;
import com.socialportal.portal.exception.issue.NoIssueFoundException;
import com.socialportal.portal.model.issues.Comment;
import com.socialportal.portal.model.user.UserEntity;
import com.socialportal.portal.repository.CommentRepository;
import com.socialportal.portal.repository.IssueRepository;
import com.socialportal.portal.repository.UserEntityRepository;
import com.socialportal.portal.service.CommentService;
import com.socialportal.portal.service.utils.Slicer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final IssueRepository issueRepository;
    private final UserEntityRepository userEntityRepository;
    private final CommentRepository commentRepository;

    @Override
    public Page<CommentDto> getComments(Long issueId, int pageNo, int itemsPerPage) {

        var commentsList = this.commentRepository.findAllCommentsForIssue(issueId)
                .orElseThrow(() -> new NoCommentFoundException("No comments found for given issue"));

        var content = commentsList.stream().map(comment -> {
            var commentContent = comment.getContent();
            var commentAuthor = comment.getUserEntity().getUsername();
            return new CommentDto(commentContent, commentAuthor);
        }).toList();

        PageRequest pageRequest = PageRequest.of(pageNo, itemsPerPage);

        return new PageImpl<>(Slicer.sliceContent(content, pageNo, itemsPerPage), pageRequest, content.size());
    }

    @Override
    public CommentDto addComment(Authentication authentication, Comment comment, long issueId) {

        var userEntity = this.getUserByUsername(authentication.getName());
        comment.setUserEntity(userEntity);

        var issue = this.issueRepository
                .findById(issueId)
                .orElseThrow(() -> new NoIssueFoundException("Cannot locate issue in db"));

        comment.setUserEntity(userEntity);
        comment.setIssue(issue);

        this.commentRepository.save(comment);
        return new CommentDto(comment.getContent(), comment.getUserEntity().getUsername());
    }

    @Override
    public void deleteComment(Authentication authentication, long commentId) {
        String role = getRoleFromAuthentication(authentication);

        if (role.equals("ADMIN")) {
            deleteCommentById(commentId);
        } else if (role.equals("USER")) {
            deleteCommentForUser(authentication.getName(), commentId);
        }
    }

    private String getRoleFromAuthentication(Authentication authentication) {
        return authentication.getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElseThrow(() -> new RuntimeException("No role associated with the given user"));
    }

    private void deleteCommentById(long commentId) {
        commentRepository.findById(commentId)
                .orElseThrow(() -> new NoCommentFoundException("Comment not found for the given id"));
        commentRepository.deleteById(commentId);
    }

    private void deleteCommentForUser(String username, long commentId) {
        var user = getUserByUsername(username);
        Comment comment = commentRepository.findCommentByUserIdAndCommentId(commentId, user.getId())
                .orElseThrow(() -> new NoCommentFoundException("Comment not found for the given id"));
        deleteCommentById(commentId);
    }

    private UserEntity getUserByUsername(String username) {
        return userEntityRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user found in the database"));
    }


}
