package com.socialportal.portal.service;

import com.socialportal.portal.dto.VoteDto;

public interface VoteService {
    VoteDto getVotesByIssueId(Long issueId);

}
