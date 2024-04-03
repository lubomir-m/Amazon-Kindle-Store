package org.example.ebookstore.repositories;

import org.example.ebookstore.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findFirst50ByAverageRatingGreaterThanEqualOrderByPurchaseCountDesc(Double avgRating);
}
