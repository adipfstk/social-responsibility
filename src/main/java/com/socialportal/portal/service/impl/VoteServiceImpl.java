package com.socialportal.portal.service.impl;

import com.socialportal.portal.dto.VoteDto;
import com.socialportal.portal.repository.VoteRepository;
import com.socialportal.portal.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {
    private final VoteRepository votesRepository;
    @Override
    public VoteDto getVotesByIssueId(Long issueId) {
        var upVotes = this.votesRepository.findUpVotesByIssueId(issueId).orElse(0L);
        var downVotes = this.votesRepository.findDownVotesByIssueId(issueId).orElse(0L);
        return new VoteDto(upVotes, downVotes);
    }

}
