package org.example.ebookstore.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.ebookstore.entities.Currency;
import org.example.ebookstore.entities.dtos.BookDto;
import org.example.ebookstore.entities.dtos.UserDto;
import org.example.ebookstore.services.interfaces.BookService;
import org.example.ebookstore.services.interfaces.CurrencyService;
import org.example.ebookstore.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {
    private final UserService userService;
    private final CurrencyService currencyService;
    private final BookService bookService;

    @Autowired
    public HomeController(UserService userService, CurrencyService currencyService, BookService bookService) {
        this.userService = userService;
        this.currencyService = currencyService;
        this.bookService = bookService;
    }

    @PostMapping("/change-currency")
    public String changeCurrency(@RequestParam String currencyCode, HttpServletResponse response,
                                 @ModelAttribute("currentUrl") String redirectUrl,
                                 @ModelAttribute("userDto") UserDto userDto,
                                 Authentication authentication, RedirectAttributes redirectAttributes) {
        if (userDto != null) {
            Optional<Currency> optional = this.currencyService.findByCodeIgnoreCase(currencyCode);
            if (optional.isPresent()) {
                Currency currency = optional.get();
                userDto.setSelectedCurrency(currency);
                this.userService.updateUserCurrency(userDto.getId(), currency);
            }
        } else {
            Cookie cookie = new Cookie("selectedCurrency", currencyCode);
            cookie.setMaxAge(60 * 60 * 24 * 7);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        System.out.println("Current URL: " + redirectUrl);

        redirectAttributes.addFlashAttribute("status", "Currency changed successfully.");
        return "redirect:" + redirectUrl;
    }

    @GetMapping({"/", "/home"})
    public String viewHomepage(Model model, HttpServletRequest request) {
        Currency currency = this.userService.getSelectedCurrency(request);
        List<BookDto> books = this.bookService.findFirst50BestSellers(currency);
        model.addAttribute("books", books);
        return "index";
    }

    @GetMapping("/about")
    public String viewAboutPage() {
        return "about";
    }

    @GetMapping("/customer-service")
    public String viewCustomerServicePage() {
        return "customer-service";
    }
}


