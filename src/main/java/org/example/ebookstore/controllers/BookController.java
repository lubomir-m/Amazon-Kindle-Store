package org.example.ebookstore.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.example.ebookstore.entities.Book;
import org.example.ebookstore.entities.Currency;
import org.example.ebookstore.entities.dtos.BookDto;
import org.example.ebookstore.services.interfaces.BookService;
import org.example.ebookstore.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class BookController {
    private final BookService bookService;
    private final UserService userService;

    @Autowired
    public BookController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    @GetMapping({"/", "/home"})
    public String viewHomepage(Model model, HttpServletRequest request) {
        Currency currency = this.userService.getSelectedCurrency(request);
        List<BookDto> books = this.bookService.findFirst50BestSellers(currency);
        model.addAttribute("books", books);
        return "index";
    }

    @GetMapping("/books/{id}")
    public String bookDetails(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        Currency currency = this.userService.getSelectedCurrency(request);
        Optional<BookDto> book = this.bookService.getDto(id, currency);
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            return "book-details";
        } else {
            return "error";
        }
    }

    @GetMapping("/categories")
    public String viewCategoryPage()
}
