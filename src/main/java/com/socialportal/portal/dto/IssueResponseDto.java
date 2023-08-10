package com.socialportal.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IssueResponseDto {
    private Long id;
    private String title;
    private String description;
    private Long upVotes;
    private Long downVotes;
}
