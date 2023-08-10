package com.socialportal.portal.service.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class Pagination {
    public static <T> Page<T> paginateRequest(PageRequest pageRequest, List<T> content) {
        var pageNo = pageRequest.getPageNumber();
        var itemsPerPage = pageRequest.getPageSize();
        var contentSize = content.size();
        int endIdx = Math.min(pageNo * itemsPerPage, contentSize);
        var startIdx  = (pageNo-1)*itemsPerPage;
        return new PageImpl<T>(content.subList(startIdx, endIdx), pageRequest, contentSize);
    }
}
