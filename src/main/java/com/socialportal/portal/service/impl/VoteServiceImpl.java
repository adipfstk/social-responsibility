package com.socialportal.portal.service.impl;

import com.socialportal.portal.dto.VoteDto;
import com.socialportal.portal.exception.issue.NoIssueFoundException;
import com.socialportal.portal.model.issues.Issue;
import com.socialportal.portal.model.issues.IssueVotes;
import com.socialportal.portal.model.user.UserEntity;
import com.socialportal.portal.repository.IssueRepository;
import com.socialportal.portal.repository.UserEntityRepository;
import com.socialportal.portal.repository.VoteRepository;
import com.socialportal.portal.service.VoteService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {
    private final VoteRepository votesRepository;
    private final UserEntityRepository userEntityRepository;
    private final IssueRepository issueRepository;


    @Override
    public VoteDto getVotesByIssueId(Long issueId) {

        final long UPVOTE = 1L;
        var upVotes = this.votesRepository
                .countVotes(UPVOTE, issueId)
                .orElse(0L);

        final long DOWNVOTE = -1L;
        var downVotes = this.votesRepository
                .countVotes(DOWNVOTE, issueId)
                .orElse(0L);

        return new VoteDto(upVotes, downVotes);
    }

    @Override
    public Integer vote(Authentication authentication, Integer vote, Long issueId) {
        String username = authentication.getName();
        Long userId;
        UserEntity dbUser = this.userEntityRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Yeah"));
        Optional<Issue> dbIssue = this.issueRepository.findById(issueId);

        userId = dbUser.getId();
        Optional<IssueVotes> dbVote = this.votesRepository.findByUserId(userId);

        if (dbVote.isPresent()) {
            IssueVotes issueVote = dbVote.get();
            issueVote.setVoteValue(vote);
            votesRepository.save(issueVote);
            return vote;
        } else if (dbIssue.isPresent()) {
            IssueVotes newIssueVote = new IssueVotes(0L, vote, dbUser, dbIssue.get());
            votesRepository.save(newIssueVote);
            return vote;
        }
        return 0;
    }

    private Integer validateVoteValues(Integer value) {
        return value > 0 ? 1 : -1;
    }



}


