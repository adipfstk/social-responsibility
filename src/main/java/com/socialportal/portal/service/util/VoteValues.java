package com.socialportal.portal.service.util;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum VoteValues {
    UP(1),
    DOWN(-1);

    private int numVal;
}