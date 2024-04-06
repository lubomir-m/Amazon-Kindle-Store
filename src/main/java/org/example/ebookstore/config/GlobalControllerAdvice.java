package org.example.ebookstore.config;

import org.example.ebookstore.entities.Currency;
import org.example.ebookstore.entities.dtos.UserDto;
import org.example.ebookstore.services.interfaces.CategoryService;
import org.example.ebookstore.services.interfaces.CurrencyService;
import org.example.ebookstore.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Optional;

@ControllerAdvice
public class GlobalControllerAdvice {
    private final CurrencyService currencyService;
    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public GlobalControllerAdvice(CurrencyService currencyService, UserService userService, CategoryService categoryService) {
        this.currencyService = currencyService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @ModelAttribute("allCurrencies")
    public List<Currency> populateCurrencies() {
        return currencyService.getAllCurrencies();
    }

    @ModelAttribute("userDto")
    public UserDto addUserDto(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated() &&
                !(authentication.getPrincipal() instanceof String)) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UserDto> optional = this.userService.getUserDtoByUsername(username);
            if (optional.isPresent()) {
                return optional.get();
            }
        }
        return null;
    }
}