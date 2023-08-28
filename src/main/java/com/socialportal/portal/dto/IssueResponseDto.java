package com.socialportal.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class IssueResponseDto {
    private Long id;
    private String title;
    private String description;
    private List<byte[]> images;
    private IssueVotesDto issueVoteStats;
    private long userVoteSelection;
}
