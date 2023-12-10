package com.book.library.book.app;

import com.book.library.book.domain.Book;
import com.book.library.book.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;

    @Override
    @Transactional
    public BookResponse save(BookRequest request) {

        Book book = Book.toDomain(request);

        bookRepository.save(book);

        return BookResponse.builder()
                .name(book.getName())
                .author(book.getAuthor())
                .description(book.getDescription())
                .build();
    }

    @Override
    @Transactional
    public BookResponse update(Long id, BookRequest bookRequest) {
        Book book = bookRepository.findById(id).orElseThrow(IllegalStateException::new);

        book.setName(bookRequest.name());
        book.setAuthor(bookRequest.author());
        book.setDescription(bookRequest.description());

        bookRepository.save(book);

        return BookResponse.builder()
                .name(book.getName())
                .author(book.getAuthor())
                .description(book.getDescription())
                .build();
    }
}
