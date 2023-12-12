package com.book.library.book.api;

import com.book.library.book.app.BookRequest;
import com.book.library.book.app.BookResponse;
import com.book.library.book.app.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/books")
    public ResponseEntity<BookResponseDto> save(@RequestBody BookRequestDto bookRequestDto) {

        BookRequest request = BookRequest.toRequest(bookRequestDto);

        BookResponse bookResponse = bookService.save(request);

        BookResponseDto bookResponseDto = BookResponseDto.builder()
                .name(bookResponse.name())
                .author(bookResponse.author())
                .description(bookResponse.description())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(bookResponseDto);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<BookResponseDto> update(@PathVariable Long id, @RequestBody BookRequestDto bookRequestDto) {
        BookRequest request = BookRequest.toRequest(bookRequestDto);

        BookResponse bookResponse = bookService.update(id, request);

        BookResponseDto bookResponseDto = BookResponseDto.builder()
                .name(bookResponse.name())
                .author(bookResponse.author())
                .description(bookResponse.description())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(bookResponseDto);
    }

    @PostMapping("/books/{bookId}/rent")
    public ResponseEntity<Object> rent(@PathVariable Long bookId, @RequestParam Long memberId) {
        bookService.rent(bookId, memberId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/books/{bookId}/return")
    public ResponseEntity<Object> returnBook(@PathVariable Long bookId, @RequestParam Long memberId) {
        bookService.returnBook(bookId, memberId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}