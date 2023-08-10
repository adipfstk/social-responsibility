package com.socialportal.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VoteDto {
    private Long upVotes;
    private Long downVotes;
}
