package com.socialportal.portal.model.geo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.socialportal.portal.model.issues.Issue;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "issue_location")
@Data
public class IssueLocation {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    protected Double latitude;

    @NotNull
    protected Double longitude;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "issueLocation")
    @JoinColumn (name = "issue_id")
    private Issue issue;
}
