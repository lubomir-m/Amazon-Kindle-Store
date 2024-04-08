package org.example.ebookstore.repositories;

import org.example.ebookstore.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findFirst10ByOrderByIdAsc();
    @Query("select r from Review r left join r.book b where b.id = :bookId or b.id is null " +
            "order by r.submissionDate desc")
    Page<Review> findAllByBookId(@Param("bookId") Long bookId, Pageable pageable);
}
