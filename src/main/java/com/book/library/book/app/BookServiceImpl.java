package com.book.library.book.app;

import com.book.library.book.domain.Book;
import com.book.library.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;

    @Override
    public BookResponse save(BookRequest request) {

        Book book = Book.toDomain(request);

        bookRepository.save(book);

        return BookResponse.builder()
                .name(book.getName())
                .author(book.getAuthor())
                .description(book.getDescription())
                .build();
    }
}
