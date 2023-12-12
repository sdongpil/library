package com.book.library.book.app;

import com.book.library.book.domain.Book;
import com.book.library.book.domain.BookRent;
import com.book.library.book.repository.BookRentRepository;
import com.book.library.book.repository.BookRepository;
import com.book.library.exception.BookNotFoundException;
import com.book.library.exception.BookRentNotFoundException;
import com.book.library.exception.MemberNotFoundException;
import com.book.library.member.domain.Member;
import com.book.library.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

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
        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);

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
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        BookRent bookRent = BookRent.builder()
                .bookId(book.getId())
                .memberId(member.getMemberId())
                .rentalDate(LocalDateTime.now())
                .build();

        bookRentRepository.save(bookRent);
    }

    @Override
    @Transactional
    public void returnBook(Long bookId, Long memberId) {
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        List<BookRent> bookRents = bookRentValidate(member);

        for (BookRent bookRent : bookRents) {
            if (bookRent.getBookId().equals(book.getId()) && bookRent.getMemberId().equals(member.getMemberId())) {
                bookRent.returnBook();
            }
        }
    }

    private List<BookRent> bookRentValidate(Member member) {
        List<BookRent> bookRents = bookRentRepository.findAllByMemberId(member.getMemberId());
        if (bookRents.isEmpty()) {
            throw new BookRentNotFoundException();
        }
        return bookRents;
    }
}
