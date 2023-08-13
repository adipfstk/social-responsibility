package com.socialportal.portal.model.issues;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.socialportal.portal.model.geo.IssueLocation;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Issue {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max= 70)
    private String title;

    @Size(min = 0, max = 10000)
    private String description;

    @JsonIgnore
    private boolean archived = false;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private IssueLocation issueLocation;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "issue")
    private List<Comments> commentsList = new ArrayList<>();

}
