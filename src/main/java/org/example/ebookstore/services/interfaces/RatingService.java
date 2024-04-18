package org.example.ebookstore.services.interfaces;

import org.example.ebookstore.entities.dtos.RatingResultDto;
import org.springframework.ui.Model;

public interface RatingService {
    RatingResultDto createRating(int ratingValue, Long userId, Long bookId);
    String deleteRating(Long bookId, Model model);
}
