package com.socialportal.portal.controller;

import com.socialportal.portal.dto.CommentDto;
import com.socialportal.portal.dto.IssueResponseDto;
import com.socialportal.portal.model.issues.Comments;
import com.socialportal.portal.model.issues.Issue;
import com.socialportal.portal.pojo.request.IssueRequest;
import com.socialportal.portal.service.CommentService;
import com.socialportal.portal.service.IssueService;
import com.socialportal.portal.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/main/")
@RequiredArgsConstructor
public class MainController {
    private final IssueService issueService;
    private final CommentService commentService;
    private final VoteService voteService;
    @PostMapping("add-issue")
    ResponseEntity<Issue> addIssue(@RequestBody IssueRequest issue) {
        return new ResponseEntity<>(this.issueService.save(issue), HttpStatus.CREATED);
    }

    @GetMapping("issues")
    ResponseEntity<Page<IssueResponseDto>> listIssues
            (
                    Authentication authentication,
                    @RequestParam Integer pageNo,
                    @RequestParam Integer noOfItems
            )
    {
        return ResponseEntity.ok(this.issueService.getIssues(authentication, pageNo, noOfItems));
    }

    @GetMapping("issue/{issueId}/get-comments")
    ResponseEntity<Page<CommentDto>> getCommentsByIssueId
            (
            @PathVariable(name ="issueId") Long issueId,
            @RequestParam(name = "pageNo") int pageNo,
            @RequestParam(name = "itemsPerPage") int itemsPerPage
            )
    {
         return ResponseEntity.ok(this.commentService.getComments(issueId, pageNo, itemsPerPage));
    }

    @PostMapping("issue/{issueId}/add-comment")
    ResponseEntity<CommentDto> addComment
            (
                    Authentication authentication,
                    @RequestBody Comments comment,
                    @PathVariable long issueId
            )
    {
        return new ResponseEntity<>(this.commentService.addComment(authentication, comment, issueId), HttpStatus.CREATED);
    }

    @PostMapping("issue/{issueId}/vote")
    ResponseEntity<Integer> vote(@PathVariable Long issueId, Authentication authentication, @RequestParam Integer voteValue) {
        return ResponseEntity.ok(this.voteService.vote(authentication, voteValue, issueId));
    }


}
