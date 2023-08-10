package com.socialportal.portal.pojo.request;

import com.socialportal.portal.model.geo.IssueLocation;
import com.socialportal.portal.model.issues.Issue;
import lombok.Data;

@Data
public class IssueRequest {
    private Issue issue;
    private IssueLocation issueLocation;
}
