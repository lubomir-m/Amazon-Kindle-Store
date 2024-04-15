package org.example.ebookstore.services.implementations;

import jakarta.transaction.Transactional;
import org.example.ebookstore.entities.Book;
import org.example.ebookstore.entities.Rating;
import org.example.ebookstore.entities.User;
import org.example.ebookstore.entities.dtos.RatingResultDto;
import org.example.ebookstore.repositories.BookRepository;
import org.example.ebookstore.repositories.RatingRepository;
import org.example.ebookstore.repositories.UserRepository;
import org.example.ebookstore.services.interfaces.RatingService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public RatingServiceImpl(RatingRepository ratingRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public RatingResultDto createRating(int ratingValue, Long userId, Long bookId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found."));
        Book book = this.bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book not found."));
        Rating rating = new Rating(user, LocalDate.now(), book, ratingValue);
        this.ratingRepository.save(rating);

        book.addRating(rating);
        this.bookRepository.save(book);
        return new RatingResultDto(book.getAverageRating(), book.getRatingsCount());
    }
}
