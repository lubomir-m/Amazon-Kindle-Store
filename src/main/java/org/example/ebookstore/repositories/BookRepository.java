package org.example.ebookstore.repositories;

import org.example.ebookstore.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findFirst50ByAverageRatingGreaterThanEqualOrderByPurchaseCountDesc(Double avgRating);
    Page<Book> findByCategoriesIdIn(List<Long> categoryIds, Pageable pageable);
    Page<Book> findByCategoriesIdInAndAverageRatingGreaterThanEqualOrderByPurchaseCountDesc
            (List<Long> categoryIds, Double avgRating, Pageable pageable);

    @Query("select b from Book b join b.categories c where c.id = :categoryId and " +
            "b.id != :bookId order by b.purchaseCount desc")
    Page<Book> getRecommendedBooks(@Param("bookId") Long bookId, @Param("categoryId") Long categoryId,
                                   Pageable pageable);
    List<Book> findFirst50000ByOrderByPurchaseCountDesc();
}
