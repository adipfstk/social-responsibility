package com.socialportal.portal.service.utils;

import java.util.List;

public class Slicer {
    public static <T> List<T> sliceContent(List<T> content, int pageNumber, int pageSize) {
        int fromIndex = pageNumber * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, content.size());
        return content.subList(fromIndex, toIndex);
    }
}
