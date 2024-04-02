package org.example.ebookstore.services.implementations;

import org.example.ebookstore.entities.Book;
import org.example.ebookstore.repositories.BookRepository;
import org.example.ebookstore.services.interfaces.BookService;
import org.example.ebookstore.services.interfaces.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final ExchangeRateService exchangeRateService;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, ExchangeRateService exchangeRateService) {
        this.bookRepository = bookRepository;
        this.exchangeRateService = exchangeRateService;
    }

    public BigDecimal round(BigDecimal value) {


        return value.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public void updateFxPrices(Book book) {

    }
}
