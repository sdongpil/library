package com.book.library.book.app;

import com.book.library.member.domain.Member;
import org.springframework.stereotype.Service;

@Service
public interface BookService {
    BookResponse save(BookRequest request);

    BookResponse update(Long id,BookRequest bookRequest);

    void rent(Long bookId, Long memberId);
}
