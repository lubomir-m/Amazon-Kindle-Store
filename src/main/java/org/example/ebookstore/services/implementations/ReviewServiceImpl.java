package org.example.ebookstore.services.implementations;

import jakarta.transaction.Transactional;
import org.example.ebookstore.entities.Book;
import org.example.ebookstore.entities.Rating;
import org.example.ebookstore.entities.Review;
import org.example.ebookstore.entities.User;
import org.example.ebookstore.entities.dtos.ReviewDto;
import org.example.ebookstore.entities.dtos.ReviewResultDto;
import org.example.ebookstore.entities.dtos.ReviewSubmissionDto;
import org.example.ebookstore.repositories.BookRepository;
import org.example.ebookstore.repositories.RatingRepository;
import org.example.ebookstore.repositories.ReviewRepository;
import org.example.ebookstore.repositories.UserRepository;
import org.example.ebookstore.services.interfaces.PlaceholderReviewService;
import org.example.ebookstore.services.interfaces.RatingService;
import org.example.ebookstore.services.interfaces.ReviewService;
import org.example.ebookstore.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;
    private final BookRepository bookRepository;
    private final UserService userService;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, ModelMapper modelMapper, UserRepository userRepository, RatingRepository ratingRepository, BookRepository bookRepository, UserService userService) {
        this.reviewRepository = reviewRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.ratingRepository = ratingRepository;
        this.bookRepository = bookRepository;
        this.userService = userService;
    }

    @Override
    public Page<ReviewDto> getReviewsByBookId(Long bookId, Pageable pageable) {
        return this.reviewRepository.findAllByBookId(bookId, pageable)
                .map(review -> this.modelMapper.map(review, ReviewDto.class));
    }

    @Override
    @Transactional
    public ReviewResultDto createReview(ReviewSubmissionDto reviewSubmissionDto, Long userId, Long bookId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found."));
        Book book = this.bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book not found."));
        Rating rating = null;
        if (this.userService.hasUserRatedBook(userId, bookId)) {
            rating = this.ratingRepository.findByUserIdAndBookId(userId, bookId).orElseThrow(() ->
                    new IllegalArgumentException("Rating not found."));
            book.removeRating(rating);
            rating.setRatingValue(reviewSubmissionDto.getReviewRating());
            rating.setSubmissionDate(LocalDate.now());
        } else {
            rating = new Rating(user, LocalDate.now(), book, reviewSubmissionDto.getReviewRating());
        }

        Review review = new Review(user, reviewSubmissionDto.getReviewTitle(), reviewSubmissionDto.getReviewText(),
                LocalDate.now(), rating, book);
        book.addRating(rating);
        book.addReviews(review);
        this.bookRepository.save(book);
        this.ratingRepository.save(rating);
        this.reviewRepository.save(review);

        ReviewDto reviewDto = this.modelMapper.map(review, ReviewDto.class);
        return new ReviewResultDto(book.getAverageRating(), book.getRatingsCount(),
                reviewDto);
    }
}
