package org.example.ebookstore.services.interfaces;

import org.example.ebookstore.entities.Book;

import java.math.BigDecimal;

public interface BookService {
    void updateFxPrices(Book book);
    BigDecimal round(BigDecimal value);
}
