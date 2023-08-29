package com.socialportal.portal.controller;

import com.socialportal.portal.model.issues.Issue;
import com.socialportal.portal.service.AdministrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdministrationService administrationService;

    @GetMapping("/issues/{pageNo}")
    ResponseEntity<Page<Issue>> getIssues
            (
                    @RequestParam(required = false, defaultValue = "false") boolean issueStatus,
                    @PathVariable int pageNo,
                    @RequestParam(required = false, defaultValue = "5") int pageSize
            ) {
        return ResponseEntity.ok(this.administrationService.getAllIssuesByStatus(issueStatus, pageNo, pageSize));
    }
    @PutMapping("/issues/{issueId}/archive")
    ResponseEntity<String> archiveIssue(long issueId) {
        this.administrationService.deactivateIssuesById(issueId);
        return new ResponseEntity<>("Issue status set to archived", HttpStatus.OK);
    }
}
