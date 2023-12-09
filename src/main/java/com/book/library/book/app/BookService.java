package com.book.library.book.app;

import org.springframework.stereotype.Service;

@Service
public interface BookService {
    BookResponse save(BookRequest request);
}
