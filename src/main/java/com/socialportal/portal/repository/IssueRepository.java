package com.socialportal.portal.repository;


import com.socialportal.portal.model.issues.Issue;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IssueRepository extends JpaRepository<Issue, Long> {


}