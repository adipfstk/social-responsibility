package com.socialportal.portal.dto;

import com.socialportal.portal.model.issues.IssueImage;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class IssueResponseDto {
    private Long id;
    private String title;
    private String description;
    private Long upVotes;
    private Long downVotes;
    private List<byte[]> images;
}
