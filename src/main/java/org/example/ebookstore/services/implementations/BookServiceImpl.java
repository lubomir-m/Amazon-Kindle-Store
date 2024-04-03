package org.example.ebookstore.services.implementations;

import org.example.ebookstore.entities.Book;
import org.example.ebookstore.entities.dtos.BookDto;
import org.example.ebookstore.repositories.BookRepository;
import org.example.ebookstore.services.interfaces.BookService;
import org.example.ebookstore.services.interfaces.ExchangeRateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final ExchangeRateService exchangeRateService;
    private final ModelMapper modelMapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, ExchangeRateService exchangeRateService, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.exchangeRateService = exchangeRateService;
        this.modelMapper = modelMapper;
    }

    public BigDecimal round(BigDecimal value) {


        return value.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public List<BookDto> findFirst50BestSellers() {
        return this.bookRepository.findFirst50ByOrderByPurchaseCountDesc()
                .stream().map(book -> this.modelMapper.map(book, BookDto.class))
                .collect(Collectors.toList());
    }

}
