package org.example.ebookstore.services.implementations;

import org.example.ebookstore.entities.BaseEntity;
import org.example.ebookstore.entities.Book;
import org.example.ebookstore.entities.Currency;
import org.example.ebookstore.entities.dtos.BookDto;
import org.example.ebookstore.repositories.BookRepository;
import org.example.ebookstore.repositories.CategoryRepository;
import org.example.ebookstore.services.interfaces.BookService;
import org.example.ebookstore.services.interfaces.ExchangeRateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final ExchangeRateService exchangeRateService;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, ExchangeRateService exchangeRateService, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.exchangeRateService = exchangeRateService;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    public BigDecimal round(BigDecimal value) {


        return value.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BookDto mapBookToDto(Book book, Currency currency) {
        BigDecimal price = null;
        String code = currency.getCode();
        if (code.equals("EUR")) {
            price = book.getPriceEur();
        } else if (code.equals("USD")) {
            price = book.getPriceUsd();
        } else if (code.equals("AUD")) {
            price = book.getPriceAud();
        } else if (code.equals("BRL")) {
            price = book.getPriceBrl();
        } else if (code.equals("INR")) {
            price = book.getPriceInr();
        } else if (code.equals("CNY")) {
            price = book.getPriceCny();
        } else if (code.equals("EGP")) {
            price = book.getPriceEgp();
        } else if (code.equals("NGN")) {
            price = book.getPriceNgn();
        } else {
            price = book.getPriceEur();
        }

        price = round(price);

        BookDto bookDto = this.modelMapper.map(book, BookDto.class);
        bookDto.setSelectedCurrency(currency);
        bookDto.setSelectedCurrencyPrice(price);
        return bookDto;
    }

    private List<Long> getCategoryAndSubcategoryIds(Long categoryId) {
        List<Long> subcategoryIds = this.categoryRepository.findAllSubcategories(categoryId)
                .stream().map(BaseEntity::getId).collect(Collectors.toCollection(ArrayList::new));
        subcategoryIds.add(categoryId);
        return subcategoryIds;
    }

    @Override
    public Page<BookDto> findByCategoryId(Long categoryId, Pageable pageable, Currency currency) {
        List<Long> categoryIds = getCategoryAndSubcategoryIds(categoryId);
        Page<Book> books = this.bookRepository.findByCategoriesIdIn(categoryIds, pageable);
        return books.map(book -> mapBookToDto(book, currency));
    }

    @Override
    public Page<BookDto> findBestsellersInCategory(Long categoryId, Pageable pageable, Currency currency) {
        List<Long> categoryIds = getCategoryAndSubcategoryIds(categoryId);
        Page<Book> books = this.bookRepository.findByCategoriesIdInAndAverageRatingGreaterThanEqualOrderByPurchaseCountDesc
                (categoryIds, 4.0, pageable);
        return books.map(book -> mapBookToDto(book, currency));
    }

    @Override
    public Sort getSortByParameter(String sortBy) {
        switch (sortBy) {
            case "averageRatingDesc":
                return Sort.by(Sort.Direction.DESC, "averageRating");
            case "publicationDateDesc":
                return Sort.by(Sort.Direction.DESC, "publicationDate");
            case "priceAsc":
                return Sort.by(Sort.Direction.ASC, "price");
            case "priceDesc":
                return Sort.by(Sort.Direction.DESC, "price");
            case "purchaseCountDesc":
            default:
                return Sort.by(Sort.Direction.DESC, "purchaseCount");
        }
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

