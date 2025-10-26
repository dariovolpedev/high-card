package it.sara.demo.service.result;

import org.springframework.data.domain.Page;

import java.util.List;

public record PagedResult<T>(
        List<T> items,
        int page,
        int size,
        long totalElements,
        int totalPages
) implements GenericResult {

    public static <T> PagedResult<T> from(Page<T> pageData) {
        return new PagedResult<>(
                pageData.getContent(),
                pageData.getNumber(),
                pageData.getSize(),
                pageData.getTotalElements(),
                pageData.getTotalPages()
        );
    }

}
