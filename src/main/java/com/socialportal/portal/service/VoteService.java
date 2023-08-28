package com.socialportal.portal.service;

import com.socialportal.portal.dto.IssueVotesDto;
import org.springframework.security.core.Authentication;

public interface VoteService {
    IssueVotesDto getVotesByIssueId(Long issueId);
    Integer vote(Authentication authentication, Integer voteVal, Long issueId);
    long getTotalVotesForUserAndIssue(long userId, long issueId);
}
