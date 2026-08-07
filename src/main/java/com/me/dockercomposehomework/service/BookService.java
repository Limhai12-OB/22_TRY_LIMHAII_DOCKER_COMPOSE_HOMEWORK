package com.me.dockercomposehomework.service;

import com.me.dockercomposehomework.dto.request.BookRequest;
import com.me.dockercomposehomework.dto.response.BookResponse;
import com.me.dockercomposehomework.dto.response.PageResponse;
import org.springframework.data.domain.Pageable;

public interface BookService {

    BookResponse create(BookRequest request);

    BookResponse getById(Long id);

    PageResponse<BookResponse> getAll(Pageable pageable);

    BookResponse update(Long id, BookRequest request);

    void delete(Long id);
}
