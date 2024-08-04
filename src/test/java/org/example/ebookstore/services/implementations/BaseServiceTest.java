package org.example.ebookstore.services.implementations;

import org.example.ebookstore.entities.Book;
import org.example.ebookstore.entities.Publisher;
import org.example.ebookstore.entities.User;
import org.example.ebookstore.repositories.BookRepository;
import org.example.ebookstore.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.longThat;
import static org.mockito.Mockito.when;

public abstract class BaseServiceTest {

    @Mock
    protected UserRepository userRepository;

    @Mock
    protected BookRepository bookRepository;

    protected User user;
    protected Book book1;
    protected Book book2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        long idNum = 10000000L;

        // Initialize User with all required fields
        user = new User();
        user.setId(idNum);
        user.setUsername("testuser");
        user.setPassword("password");
        user.setAge(25);
        user.setEmail("testuser@example.com");
        user.setFirstName("Test");
        user.setLastName("User");


    }
}