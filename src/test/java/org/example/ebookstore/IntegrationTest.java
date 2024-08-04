package org.example.ebookstore;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.example.ebookstore.entities.Book;
import org.example.ebookstore.entities.User;
import org.example.ebookstore.entities.Publisher;
import org.example.ebookstore.repositories.BookRepository;
import org.example.ebookstore.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private IntegrationTest userIntegrationTest;

    private User user;
    private Book book1;
    private Book book2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize User with all required fields
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password");
        user.setAge(25); // Set a valid age
        user.setEmail("testuser@example.com");
        user.setFirstName("Test");
        user.setLastName("User");

        // Initialize Book1 with all required fields
        book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Test Book 1");
        book1.setDescription("Description for Test Book 1");
        book1.setPublicationDate(LocalDate.now());
        Publisher publisher = new Publisher();
        publisher.setId(1L);
        publisher.setName("Test Publisher");
        book1.setPublisher(publisher);
        book1.setPriceEur(BigDecimal.valueOf(10.99));
        book1.setCoverColor("Blue");
        book1.setSearchColumn("Test Book 1");
        book1.setAverageRating(0.0);
        book1.setRatingsCount(0L);
        book1.setPurchaseCount(0L);

        // Initialize Book2 with all required fields
        book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Test Book 2");
        book2.setDescription("Description for Test Book 2");
        book2.setPublicationDate(LocalDate.now());
        book2.setPublisher(publisher);
        book2.setPriceEur(BigDecimal.valueOf(12.99));
        book2.setCoverColor("Red");
        book2.setSearchColumn("Test Book 2");
        book2.setAverageRating(0.0);
        book2.setRatingsCount(0L);
        book2.setPurchaseCount(0L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));
        when(bookRepository.findById(2L)).thenReturn(Optional.of(book2));
    }

    @Test
    public void testAddBooksToWishlistAndFailToRate() throws Exception {
        // Add books to wishlist
        mockMvc.perform(post("/wishlist/add")
                        .param("userId", user.getId().toString())
                        .param("bookId", book1.getId().toString()))
                .andExpect(status().isOk());

        mockMvc.perform(post("/wishlist/add")
                        .param("userId", user.getId().toString())
                        .param("bookId", book2.getId().toString()))
                .andExpect(status().isOk());

        // Attempt to rate books without purchasing
        mockMvc.perform(post("/rate")
                        .param("userId", user.getId().toString())
                        .param("bookId", book1.getId().toString())
                        .param("rating", "5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        mockMvc.perform(post("/rate")
                        .param("userId", user.getId().toString())
                        .param("bookId", book2.getId().toString())
                        .param("rating", "4")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}