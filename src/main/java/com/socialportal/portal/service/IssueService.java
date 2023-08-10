package com.socialportal.portal.service;

import com.socialportal.portal.dto.IssueResponseDto;
import com.socialportal.portal.exception.issue.NoIssueFoundException;
import com.socialportal.portal.model.issues.Issue;
import com.socialportal.portal.pojo.request.IssueRequest;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public interface IssueService {
    Issue save(IssueRequest issueRequest);
    Page<IssueResponseDto> getIssues(Authentication authentication, int pageNumber, int pageSize) throws NoIssueFoundException, UsernameNotFoundException;

}
