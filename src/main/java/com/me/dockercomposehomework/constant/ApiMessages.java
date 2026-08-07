package com.me.dockercomposehomework.constant;

public final class ApiMessages {

    private ApiMessages() {
    }

    // Success messages
    public static final String BOOK_CREATED = "Book created successfully";
    public static final String BOOK_RETRIEVED = "Book retrieved successfully";
    public static final String BOOKS_RETRIEVED = "Books retrieved successfully";
    public static final String BOOK_UPDATED = "Book updated successfully";
    public static final String BOOK_DELETED = "Book deleted successfully";

    // Error messages
    public static final String BOOK_NOT_FOUND = "Book not found with id %s";
    public static final String MALFORMED_REQUEST_BODY = "Malformed or missing request body";
    public static final String MISSING_PARAMETER = "Missing required parameter: %s";
    public static final String TYPE_MISMATCH = "Parameter '%s' should be of type %s";
    public static final String DATA_INTEGRITY_VIOLATION = "Request violates a data constraint";
    public static final String INVALID_SORT_PROPERTY = "Invalid sort/property: %s";
    public static final String INVALID_QUERY_PARAMETERS = "Invalid sort or query parameters";
    public static final String RESOURCE_NOT_FOUND = "Resource not found";
    public static final String UNEXPECTED_ERROR = "Unexpected error occurred";
}
