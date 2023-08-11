package com.socialportal.portal.service;

import com.socialportal.portal.dto.VoteDto;
import org.springframework.security.core.Authentication;

public interface VoteService {
    VoteDto getVotesByIssueId(Long issueId);
    Integer vote(Authentication authentication, Integer voteVal, Long issueId);
}
