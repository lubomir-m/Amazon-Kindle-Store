package org.example.ebookstore.services.implementations;

import org.example.ebookstore.entities.BaseEntity;
import org.example.ebookstore.entities.Book;
import org.example.ebookstore.entities.Category;
import org.example.ebookstore.entities.Currency;
import org.example.ebookstore.entities.dtos.BookDto;
import org.example.ebookstore.repositories.BookRepository;
import org.example.ebookstore.repositories.CategoryRepository;
import org.example.ebookstore.repositories.ReviewRepository;
import org.example.ebookstore.services.interfaces.BookService;
import org.example.ebookstore.services.interfaces.ExchangeRateService;
import org.example.ebookstore.services.interfaces.PlaceholderReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public BigDecimal getPriceInSelectedCurrency(Book book, Currency currency) {
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

        return round(price);
    }

    @Override
    public BookDto mapBookToDto(Book book, Currency currency) {
        BigDecimal price = getPriceInSelectedCurrency(book, currency);
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
    public List<BookDto> findFirst50BestSellers(Currency currency) {
        return this.bookRepository.findFirst50ByAverageRatingGreaterThanEqualOrderByPurchaseCountDesc(0.1)
                .stream().map(book -> mapBookToDto(book, currency))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BookDto> getDto(Long id, Currency currency) {
        Optional<Book> optional = this.bookRepository.findById(id);
        return optional.map(book -> mapBookToDto(book, currency));
    }

    @Override
    public List<BookDto> getRecommendedBooks(Long id, Currency currency) {
        Book book = this.bookRepository.findById(id).get();
        Long categoryId = book.getCategories().stream().filter(c -> !c.getId().equals(1L))
                .findFirst().map(Category::getId).get();
        Pageable pageable = PageRequest.of(0, 8, Sort.by("purchaseCount").descending());
        Page<Book> page = this.bookRepository.getRecommendedBooks(id, categoryId, pageable);
        return page.getContent().stream().map(b -> mapBookToDto(b, currency))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Page<BookDto> findByAuthorId(Long authorId, Pageable pageable, Currency currency) {
        Page<Book> books = this.bookRepository.findByAuthorsId(authorId, pageable);
        return books.map(book -> mapBookToDto(book, currency));
    }

    @Override
    public Page<BookDto> findByPublisherId(Long publisherId, Pageable pageable, Currency currency) {
        return this.bookRepository.findByPublisherId(publisherId, pageable)
                .map(book -> mapBookToDto(book, currency));
    }

    @Override
    public Page<BookDto> findByUserId(Long userId, Pageable pageable, Currency currency) {
        return this.bookRepository.findByUserId(userId, pageable)
                .map(book -> mapBookToDto(book, currency));
    }

    @Override
    public Page<BookDto> findByShoppingCartId(Long cartId, Pageable pageable, Currency currency) {
        return this.bookRepository.findByShoppingCartsId(cartId, pageable)
                .map(book -> mapBookToDto(book, currency));
    }

    @Override
    public Page<BookDto> findByWishlistId(Long wishlistId, Pageable pageable, Currency currency) {
        return this.bookRepository.findByWishlistsId(wishlistId, pageable)
                .map(book -> mapBookToDto(book, currency));
    }
}

