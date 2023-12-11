package com.book.library.book.repository;

import com.book.library.book.domain.BookRent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRentRepository extends JpaRepository<BookRent, Long> {
}
