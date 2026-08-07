package com.me.dockercomposehomework.controller;

import com.me.dockercomposehomework.dto.request.BookRequest;
import com.me.dockercomposehomework.dto.response.ApiResponse;
import com.me.dockercomposehomework.dto.response.BookResponse;
import com.me.dockercomposehomework.dto.response.PageResponse;
import com.me.dockercomposehomework.service.BookService;
import static com.me.dockercomposehomework.constant.ApiMessages.BOOK_CREATED;
import static com.me.dockercomposehomework.constant.ApiMessages.BOOK_DELETED;
import static com.me.dockercomposehomework.constant.ApiMessages.BOOK_RETRIEVED;
import static com.me.dockercomposehomework.constant.ApiMessages.BOOK_UPDATED;
import static com.me.dockercomposehomework.constant.ApiMessages.BOOKS_RETRIEVED;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "Books", description = "Book management APIs")
@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<ApiResponse<BookResponse>> create(@Valid @RequestBody BookRequest request) {
        BookResponse response = bookService.create(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(
                        HttpStatus.CREATED,
                        BOOK_CREATED,
                        response
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, BOOK_RETRIEVED, bookService.getById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<BookResponse>>> getAll(
            @ParameterObject @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, BOOKS_RETRIEVED, bookService.getAll(pageable)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> update(@PathVariable Long id, @Valid @RequestBody BookRequest request) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, BOOK_UPDATED, bookService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, BOOK_DELETED, null));
    }
}
