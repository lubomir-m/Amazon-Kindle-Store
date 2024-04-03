package org.example.ebookstore.controllers;

import org.example.ebookstore.entities.Book;
import org.example.ebookstore.entities.dtos.BookDto;
import org.example.ebookstore.services.interfaces.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping({"/", "/home", "/bestsellers"})
    public String viewHomepage(Model model) {
        List<BookDto> books = this.bookService.findFirst50BestSellers();
        model.addAttribute("books", books);
        return "index";
    }
}
