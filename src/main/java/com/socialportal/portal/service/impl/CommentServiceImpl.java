package com.socialportal.portal.service.impl;

import com.socialportal.portal.dto.CommentDto;
import com.socialportal.portal.exception.issue.NoIssueFoundException;
import com.socialportal.portal.model.issues.Comments;
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

        var commentsList = this.commentRepository.findAllCommentsForIssue(issueId);
        if (commentsList.isEmpty()) {
            throw new NoIssueFoundException("No comments");
        }

        var content = commentsList.stream().map(comment -> {
            var commentContent = comment.getContent();
            var commentAuthor = comment.getUserEntity().getUsername();
            return new CommentDto(commentContent, commentAuthor);
        }).toList();

        PageRequest pageRequest = PageRequest.of(pageNo, itemsPerPage);

        return new PageImpl<>(Slicer.sliceContent(content, pageNo, itemsPerPage), pageRequest, content.size());
    }

    @Override
    public CommentDto addComment(Authentication authentication, Comments comment, long issueId) {

        var userEntity = this.userEntityRepository
                .findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("The user cannot be located in db"));
        comment.setUserEntity(userEntity);

        var issue = this.issueRepository
                .findById(issueId)
                .orElseThrow(() -> new NoIssueFoundException("Cannot locate issue in db"));

        comment.setUserEntity(userEntity);
        comment.setIssue(issue);

        this.commentRepository.save(comment);
        return new CommentDto(comment.getContent(), comment.getUserEntity().getUsername());
    }
}
