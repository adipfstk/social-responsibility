package com.socialportal.portal.repository;


import com.socialportal.portal.model.issues.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface IssueRepository extends JpaRepository<Issue, Long> {
    @Query(value = "SELECT * FROM issues WHERE issues.archived = ?1",
            nativeQuery = true)
    Page<Issue> findAllByStatus(boolean status, Pageable pageable);
}
