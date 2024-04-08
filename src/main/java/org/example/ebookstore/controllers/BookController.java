package org.example.ebookstore.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.example.ebookstore.entities.Book;
import org.example.ebookstore.entities.Currency;
import org.example.ebookstore.entities.Review;
import org.example.ebookstore.entities.dtos.BookDto;
import org.example.ebookstore.entities.dtos.CategoryDto;
import org.example.ebookstore.services.interfaces.BookService;
import org.example.ebookstore.services.interfaces.CategoryService;
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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Controller
public class BookController {
    private final BookService bookService;
    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public BookController(BookService bookService, UserService userService, CategoryService categoryService) {
        this.bookService = bookService;
        this.userService = userService;
        this.categoryService = categoryService;
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
        Optional<BookDto> bookDto = this.bookService.getDto(id, currency);
        if (bookDto.isPresent()) {
            model.addAttribute("book", bookDto.get());
            // TODO: add 8 recommended books from a similar category

            List<BookDto> books = this.bookService.getRecommendedBooks(id, currency);
            model.addAttribute("recommendedBooks", books);


            return "book-details";
        } else {
            return "error";
        }
    }

    @GetMapping("/categories/{id}")
    public String viewCategoryPage(@PathVariable("id") Long id,
                                   @RequestParam(defaultValue = "0") int page,
                                   Model model, HttpServletRequest request,
                                   @RequestParam(defaultValue = "purchaseCountDesc") String sortBy) {
        Optional<CategoryDto> optional = this.categoryService.getCategoryDtoById(id);
        if (optional.isEmpty()) {
            return "error";
        }

        CategoryDto currentCategory = optional.get();
        List<CategoryDto> directSubcategories = this.categoryService.getDirectSubcategories(id);
        List<CategoryDto> parentCategories = this.categoryService.getParentCategories(id);
        model.addAttribute("currentCategory", currentCategory);
        model.addAttribute("directSubcategories", directSubcategories);
        model.addAttribute("parentCategories", parentCategories);

        Currency currency = this.userService.getSelectedCurrency(request);
        Sort sort = this.bookService.getSortByParameter(sortBy);
        Pageable pageable = PageRequest.of(page, 16, sort);

        Page<BookDto> bookDtoPage = null;
        if (sortBy.equals("purchaseCountDesc")) {
            bookDtoPage = this.bookService.findBestsellersInCategory(id, pageable, currency);
        } else {
            bookDtoPage = this.bookService.findByCategoryId(id, pageable, currency);
        }

        model.addAttribute("books", bookDtoPage.getContent());
        model.addAttribute("currentPage", bookDtoPage.getNumber());
        model.addAttribute("totalPages", bookDtoPage.getTotalPages());
        model.addAttribute("currentSort", sortBy);
        model.addAttribute("numberOfBooks", bookDtoPage.getTotalElements());

        Map<String, String> sortOptions = Map.of(
                "purchaseCountDesc", "Best Sellers",
                "priceAsc", "Price: Low to High",
                "priceDesc", "Price: High to Low",
                "averageRatingDesc", "Avg. Customer Review",
                "publicationDateDesc", "Publication Date"
        );
        model.addAttribute("sortOptions", sortOptions);

        int startIndex = page * pageable.getPageSize() + 1;
        int endIndex = startIndex + pageable.getPageSize() - 1;
        if (endIndex > bookDtoPage.getTotalElements()) {
            endIndex = (int) bookDtoPage.getTotalElements();
        }
        model.addAttribute("startIndex", startIndex);
        model.addAttribute("endIndex", endIndex);

        return "category";
    }
}
