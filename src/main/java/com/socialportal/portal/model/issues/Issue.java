package com.socialportal.portal.model.issues;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.socialportal.portal.model.geo.IssueLocation;
import com.socialportal.portal.model.user.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Issue {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @AssertFalse
    private boolean archived;

    @Size(max = 70)
    private String title;

    @Size(min = 0, max = 10000)
    private String description;

    @JsonIgnore
    private final Date date = new Date();

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private IssueLocation issueLocation;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity author;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "owner_id")
    private List<IssueImage> images = new ArrayList<>();
}
