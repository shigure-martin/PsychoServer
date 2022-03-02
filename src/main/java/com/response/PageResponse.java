package com.response;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PageResponse<T> {

    public static <T> Page<T> build(List<T> source, Pageable pageable) {
        int begin = pageable.getPageSize() * pageable.getPageNumber();
        int end = begin + pageable.getPageSize() > source.size() ? source.size() : begin + pageable.getPageSize();

        List<T> result = source.subList(begin, end);

        return new PageImpl<>(result, pageable, source.size());
    }

    public static <T> Page<T> build(int count, List<T> source, Pageable pageable) {
        return new PageImpl<>(source, pageable, count);
    }
}
