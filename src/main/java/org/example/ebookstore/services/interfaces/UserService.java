package org.example.ebookstore.services.interfaces;

import org.example.ebookstore.entities.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
}
