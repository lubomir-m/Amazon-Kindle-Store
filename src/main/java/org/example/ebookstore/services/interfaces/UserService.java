package org.example.ebookstore.services.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import org.example.ebookstore.entities.Currency;
import org.example.ebookstore.entities.User;
import org.example.ebookstore.entities.dtos.UserDto;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
    User save(User user);
    Optional<UserDto> getUserDtoByUsername(String username);
    Currency getSelectedCurrency(HttpServletRequest request);
    void updateUserCurrency(Long userId, Currency currency);
}
