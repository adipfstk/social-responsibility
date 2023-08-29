package com.socialportal.portal.service.impl;

import com.socialportal.portal.exception.issue.NoIssueFoundException;
import com.socialportal.portal.exception.user.NoRolesDataBase;
import com.socialportal.portal.exception.user.NoUserFoundException;
import com.socialportal.portal.model.issues.Issue;
import com.socialportal.portal.repository.IssueRepository;
import com.socialportal.portal.repository.RoleRepository;
import com.socialportal.portal.repository.UserEntityRepository;
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
    private final UserEntityRepository userEntityRepository;
    private final RoleRepository roleRepository;

    @Override
    public Page<Issue> getAllIssuesByStatus(boolean status, int pageNo, int pageSize) {
        Pageable pageRequest = PageRequest.of(pageNo, pageSize);
        return this.issueRepository.findAllByStatus(status, pageRequest);
    }

    @Override
    public void deactivateIssuesById(long issueId) {
        var issue = this.issueRepository.findById(issueId)
                .orElseThrow(() -> new NoIssueFoundException("No issue found in database"));
        issue.setArchived(true);
        this.issueRepository.save(issue);
    }

    @Override
    public void createAdmin(long userId) {
        var user = this.userEntityRepository.findById(userId)
                .orElseThrow(() -> new NoUserFoundException("User cannot be found in db"));
        user.setRole(this.roleRepository
                .findByName("ADMIN")
                .orElseThrow(() -> new NoRolesDataBase("No role present in db")));
        this.userEntityRepository.save(user);

    }

}
