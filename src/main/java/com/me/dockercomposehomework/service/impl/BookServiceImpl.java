package com.me.dockercomposehomework.service.impl;

import com.me.dockercomposehomework.dto.request.BookRequest;
import com.me.dockercomposehomework.dto.response.BookResponse;
import com.me.dockercomposehomework.dto.response.PageResponse;
import com.me.dockercomposehomework.entity.Book;
import com.me.dockercomposehomework.exception.ResourceNotFoundException;
import com.me.dockercomposehomework.mapper.BookMapper;
import com.me.dockercomposehomework.repository.BookRepository;
import com.me.dockercomposehomework.service.BookService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.me.dockercomposehomework.constant.ApiMessages.BOOK_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    @Transactional
    public BookResponse create(BookRequest request) {
        Book book = bookMapper.toEntity(request);
        BookResponse response = bookMapper.toResponse(bookRepository.save(book));
        log.info("Created book id={}", response.getId());
        return response;
    }

    @Override
    public BookResponse getById(Long id) {
        return bookMapper.toResponse(findBookOrThrow(id));
    }

    @Override
    public PageResponse<BookResponse> getAll(Pageable pageable) {
        Page<BookResponse> page = bookRepository.findAll(pageable).map(bookMapper::toResponse);
        log.debug("Fetched books page={} size={} totalElements={}", page.getNumber(), page.getSize(), page.getTotalElements());
        return PageResponse.from(page);
    }

    @Override
    @Transactional
    public BookResponse update(Long id, BookRequest request) {
        Book book = findBookOrThrow(id);
        bookMapper.updateEntityFromRequest(request, book);
        BookResponse response = bookMapper.toResponse(bookRepository.save(book));
        log.info("Updated book id={}", id);
        return response;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException(BOOK_NOT_FOUND.formatted(id));
        }
        bookRepository.deleteById(id);
        log.info("Deleted book id={}", id);
    }

    private Book findBookOrThrow(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(BOOK_NOT_FOUND.formatted(id)));
    }
}
