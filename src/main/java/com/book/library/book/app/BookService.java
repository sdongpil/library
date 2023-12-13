package com.book.library.book.app;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    BookResponse save(BookRequest request);

    BookResponse update(Long id,BookRequest bookRequest);

    void rent(Long bookId, Long memberId);

    void returnBook(Long bookId, Long memberId);

    List<BookRentResponse> getBookRentHistory(String memberId);
}
