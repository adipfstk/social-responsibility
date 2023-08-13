package com.socialportal.portal.repository;


import com.socialportal.portal.model.issues.Issue;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface IssueRepository extends JpaRepository<Issue, Long> {
    Page <Issue> findAllByArchived(boolean value, Pageable pagination);
    List <Issue> findAllByArchived(boolean value);
}
