package org.example.ebookstore.services.implementations;

import jakarta.transaction.Transactional;
import org.example.ebookstore.entities.Book;
import org.example.ebookstore.entities.Rating;
import org.example.ebookstore.entities.Review;
import org.example.ebookstore.entities.User;
import org.example.ebookstore.entities.dtos.RatingResultDto;
import org.example.ebookstore.entities.dtos.UserDto;
import org.example.ebookstore.repositories.BookRepository;
import org.example.ebookstore.repositories.RatingRepository;
import org.example.ebookstore.repositories.ReviewRepository;
import org.example.ebookstore.repositories.UserRepository;
import org.example.ebookstore.services.interfaces.RatingService;
import org.example.ebookstore.services.interfaces.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;
    private final UserService userService;

    public RatingServiceImpl(RatingRepository ratingRepository, UserRepository userRepository, BookRepository bookRepository, ReviewRepository reviewRepository, UserService userService) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public RatingResultDto createRating(int ratingValue, Model model, Long bookId) {
        UserDto userDto = (UserDto) model.getAttribute("userDto");
        if (userDto == null) {
            throw new IllegalArgumentException("You have to be logged in.");
        }
        Long userId = userDto.getId();

        if (!this.userService.hasUserPurchasedBook(userId, bookId)) {
            throw new IllegalArgumentException("You can only rate books that you have purchased.");
        }
        if (this.userService.hasUserRatedBook(userId, bookId)) {
            throw new IllegalArgumentException("You have already rated this book.");
        }
        User user = this.userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found."));
        Book book = this.bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book not found."));
        Rating rating = new Rating(user, LocalDate.now(), book, ratingValue);
        this.ratingRepository.save(rating);

        book.addRating(rating);
        this.bookRepository.save(book);
        return new RatingResultDto(book.getAverageRating(), book.getRatingsCount());
    }

    @Override
    @Transactional
    public String deleteRating(Long bookId, Model model) {
        UserDto userDto = (UserDto) model.getAttribute("userDto");
        if (userDto == null) {
            throw new IllegalArgumentException("You need to be logged in.");
        }
        Optional<Rating> optional = this.ratingRepository.findByUserIdAndBookId(userDto.getId(), bookId);
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("You have not rated this book.");
        }

        Rating rating = optional.get();
        Book book = this.bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book not found."));
        Optional<Review> optionalReview = this.reviewRepository.findByRatingId(rating.getId());

        book.removeRating(rating);
        if (optionalReview.isPresent()) {
            Review review = optionalReview.get();
            book.removeReviews(review);
            this.reviewRepository.delete(review);
            return "The rating and the review associated with it were deleted.";
        }
        this.ratingRepository.delete(rating);

        return "The rating was deleted.";
    }
}
