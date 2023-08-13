package com.socialportal.portal.service.impl;

import com.socialportal.portal.model.issues.Issue;
import com.socialportal.portal.repository.IssueRepository;
import com.socialportal.portal.service.AdministrationService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdministrationServiceImpl implements AdministrationService {
    private final IssueRepository issueRepository;
    @Override
    public Page<Issue> getAllIssuesByStatus(boolean status, int pageNo, int pageSize) {
        Pageable pageRequest = PageRequest.of(pageNo, pageSize);
        System.out.println(this.issueRepository.findAllByArchived(status, pageRequest).getContent().size());
        return this.issueRepository.findAllByArchived(status, pageRequest);
    }

}
