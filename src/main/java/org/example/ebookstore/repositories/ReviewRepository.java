package org.example.ebookstore.repositories;

import org.example.ebookstore.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findFirst10ByOrderByIdAsc();
}
