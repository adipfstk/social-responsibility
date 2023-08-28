package com.socialportal.portal.service.impl;

import com.socialportal.portal.dto.IssueVotesDto;
import com.socialportal.portal.exception.issue.NoIssueFoundException;
import com.socialportal.portal.model.issues.Issue;
import com.socialportal.portal.model.issues.IssueVotes;
import com.socialportal.portal.model.user.UserEntity;
import com.socialportal.portal.repository.IssueRepository;
import com.socialportal.portal.repository.UserEntityRepository;
import com.socialportal.portal.repository.VoteRepository;
import com.socialportal.portal.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {
    private final VoteRepository voteRepository;
    private final UserEntityRepository userEntityRepository;
    private final IssueRepository issueRepository;

    @Override
    public IssueVotesDto getVotesByIssueId(Long issueId) {
        long UPVOTE = 1;
        long upVotes = voteRepository.countVotes(UPVOTE, issueId).orElse(0L);
        long DOWNVOTE = -1;
        long downVotes = voteRepository.countVotes(DOWNVOTE, issueId).orElse(0L);
        return new IssueVotesDto(upVotes, downVotes);
    }

    @Override
    public Integer vote(Authentication authentication, Integer voteValue, Long issueId) {
        voteValue = validateVoteValue(voteValue);
        String username = authentication.getName();
        UserEntity user = userEntityRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found in database!"));
        Optional<Issue> optionalIssue = issueRepository.findById(issueId);
        if (optionalIssue.isEmpty()) {
            return 0;
        }
        Issue issue = optionalIssue.get();
        Long userId = user.getId();
        Optional<IssueVotes> optionalVote = voteRepository.findByUserId(userId);
        if (optionalVote.isPresent()) {
            updateVoteIfDifferent(optionalVote.get(), voteValue);
        } else {
            saveNewIssueVote(user, issue, voteValue);
        }
        checkForDeactivation(issueId);
        return voteValue;
    }

    public long getTotalVotesForUserAndIssue(long userId, long issueId) {
        return userId == 0L ? 0L : this.voteRepository.getTotalVotesForUserAndIssue(userId, issueId).orElse(0L);
    }

    private void updateVoteIfDifferent(IssueVotes issueVote, Integer voteValue) {
        if (!issueVote.getVoteValue().equals(voteValue)) {
            issueVote.setVoteValue(voteValue);
            voteRepository.save(issueVote);
        }
    }

    private void saveNewIssueVote(UserEntity user, Issue issue, Integer voteValue) {
        IssueVotes newIssueVote = new IssueVotes(0L, voteValue, user, issue);
        voteRepository.save(newIssueVote);
    }

    private void checkForDeactivation(Long issueId) {
        IssueVotesDto votes = getVotesByIssueId(issueId);
        if (shouldDeactivateIssue(votes)) {
            deactivateIssue(issueId);
        }
    }

    private boolean shouldDeactivateIssue(IssueVotesDto votes) {
        return votes.getDownVotes() > 0 && votes.getUpVotes() > 0 && votes.getDownVotes() >= 2 * votes.getUpVotes();
    }

    private void deactivateIssue(Long issueId) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new NoIssueFoundException("No issue present in the database"));
        issue.setArchived(true);
    }

    private Integer validateVoteValue(Integer value) {
        return value > 0 ? 1 : -1;
    }
}
