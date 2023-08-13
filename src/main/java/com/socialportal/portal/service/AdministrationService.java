package com.socialportal.portal.service;

import com.socialportal.portal.model.issues.Issue;

import org.springframework.data.domain.Page;

public interface AdministrationService {
    Page<Issue> getAllIssuesByStatus(boolean status, int pageNo, int pageSize);
}
