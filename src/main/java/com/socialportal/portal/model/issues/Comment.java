package com.socialportal.portal.model.issues;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.socialportal.portal.model.user.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "issue_comments")
@Data
public class Comment {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @JsonIgnore
    @ManyToOne
    private UserEntity userEntity;
    @ManyToOne
    private Issue issue;
}
