package com.socialportal.portal.repository;

import com.socialportal.portal.model.issues.IssueVotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<IssueVotes, Long> {
    @Query(value = "SELECT SUM(iv.up_vote) FROM issues_votes iv WHERE iv.issue_id = ?1", nativeQuery = true)
    Optional<Long> findUpVotesByIssueId(Long issueId);

    @Query(value = "SELECT SUM(iv.down_vote) FROM issues_votes iv WHERE iv.issue_id = ?1", nativeQuery = true)
    Optional<Long> findDownVotesByIssueId(Long issueId);


}
