package com.socialportal.portal.controller;

import com.socialportal.portal.model.issues.Issue;
import com.socialportal.portal.service.AdministrationService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
