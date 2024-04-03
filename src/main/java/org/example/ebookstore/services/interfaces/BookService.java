package org.example.ebookstore.services.interfaces;

import org.example.ebookstore.entities.Book;
import org.example.ebookstore.entities.dtos.BookDto;

import java.math.BigDecimal;
import java.util.List;

public interface BookService {
    BigDecimal round(BigDecimal value);
    List<BookDto> findFirst50BestSellers();
}
