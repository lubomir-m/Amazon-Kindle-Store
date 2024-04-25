package org.example.ebookstore.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.example.ebookstore.entities.Book;
import org.example.ebookstore.entities.Currency;
import org.example.ebookstore.entities.Review;
import org.example.ebookstore.entities.dtos.BookDto;
import org.example.ebookstore.entities.dtos.CategoryDto;
import org.example.ebookstore.entities.dtos.ReviewDto;
import org.example.ebookstore.services.interfaces.BookService;
import org.example.ebookstore.services.interfaces.CategoryService;
import org.example.ebookstore.services.interfaces.ReviewService;
import org.example.ebookstore.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class BookController {
    private final BookService bookService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ReviewService reviewService;

    @Autowired
    public BookController(BookService bookService, UserService userService, CategoryService categoryService, ReviewService reviewService) {
        this.bookService = bookService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.reviewService = reviewService;
    }

    @GetMapping("/books/{id}")
    public String bookDetails(@PathVariable("id") Long id, Model model, HttpServletRequest request,
                              @RequestParam(defaultValue = "0") int page) {

        Currency currency = this.userService.getSelectedCurrency(request);
        Optional<BookDto> bookDto = this.bookService.getDto(id, currency);
        if (bookDto.isPresent()) {
            model.addAttribute("book", bookDto.get());

            List<BookDto> books = this.bookService.getRecommendedBooks(id, currency);
            model.addAttribute("books", books);
            Pageable pageable = PageRequest.of(page, 10);

            Page<ReviewDto> reviewPage = this.reviewService.getReviewsByBookId(id, pageable);
            List<ReviewDto> reviews = new ArrayList<>(reviewPage.getContent());
            int placeholderCount = 10 - reviews.size();
            List<ReviewDto> placeholderReviews = this.reviewService.getPlaceholderReviews(placeholderCount);
            reviews.addAll(placeholderReviews);

            model.addAttribute("reviews", reviews);
            model.addAttribute("currentReviewPage", pageable.getPageNumber());
            model.addAttribute("totalReviewPages", reviewPage.getTotalPages());

            return "book-details";
        } else {
            return "error";
        }
    }


}
