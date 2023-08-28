package com.socialportal.portal.repository;

import com.socialportal.portal.model.issues.IssueVotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<IssueVotes, Long> {

    @Query(value = "SELECT COUNT(vote_value) "
    + "FROM issues_votes iv "
    + "WHERE iv.vote_value = ?1 AND iv.issue_id = ?2", nativeQuery = true)
    Optional<Long> countVotes(Long voteDirection, Long issueId);
    Optional<IssueVotes> findByUserId(Long issueId);

    @Query(value = "SELECT vote_value "
            + "FROM issues_votes iv "
            + "WHERE iv.user_id = ?1 AND iv.issue_id = ?2", nativeQuery = true)
    Optional<Long> getTotalVotesForUserAndIssue(long userId, long issueId);

}
