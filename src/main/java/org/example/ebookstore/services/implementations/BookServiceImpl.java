package org.example.ebookstore.services.implementations;

import org.example.ebookstore.entities.Book;
import org.example.ebookstore.entities.Currency;
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
import java.util.Optional;
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
    public BookDto mapBookToDto(Book book, Currency currency) {
        BookDto bookDto = this.modelMapper.map(book, BookDto.class);
        bookDto.setSelectedCurrency(currency);
        bookDto.setSelectedCurrencyPrice();
        return bookDto;
    }

    @Override
    public List<BookDto> findFirst50BestSellers(Currency currency) {
        return this.bookRepository.findFirst50ByAverageRatingGreaterThanEqualOrderByPurchaseCountDesc(4.0)
                .stream().map(book -> mapBookToDto(book, currency))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BookDto> getDto(Long id, Currency currency) {
        Optional<Book> optional = this.bookRepository.findById(id);
        return optional.map(book -> mapBookToDto(book, currency));
    }

}
