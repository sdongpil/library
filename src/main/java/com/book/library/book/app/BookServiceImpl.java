package com.book.library.book.app;

import com.book.library.book.domain.Book;
import com.book.library.book.domain.BookRent;
import com.book.library.book.repository.BookRentRepository;
import com.book.library.book.repository.BookRepository;
import com.book.library.member.domain.Member;
import com.book.library.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final BookRentRepository bookRentRepository;

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

    @Override
    @Transactional
    public void rent(Long bookId, Long memberId) {
        Book book = bookRepository.findById(bookId).orElseThrow();
        Member member = memberRepository.findById(memberId).orElseThrow();
        // TODO: 12/11/23 대여 이력이 있는지 검증하기

        BookRent bookRent = BookRent.builder()
                .bookId(book.getId())
                .memberId(member.getMemberId())
                .rentalDate(LocalDateTime.now())
                .build();

        bookRentRepository.save(bookRent);
    }
}
