package org.example.ebookstore.services.interfaces;

import org.example.ebookstore.entities.Book;
import org.example.ebookstore.entities.dtos.BookDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BookService {
    BigDecimal round(BigDecimal value);
    List<BookDto> findFirst50BestSellers();
    Optional<BookDto> getDto(Long id);
}
