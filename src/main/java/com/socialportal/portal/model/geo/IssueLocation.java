package com.socialportal.portal.model.geo;

import com.socialportal.portal.model.issues.Issue;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "issue_location")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IssueLocation extends Location {
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "issueLocation")
    @JoinColumn (name = "issue_id")
    private Issue issue;
}
