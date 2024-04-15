package org.example.ebookstore.repositories;

import org.example.ebookstore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findFirst10ByOrderByIdAsc();
    //TODO: check if this query works
    @Query("select exists (select 1 from Order o join o.books b " +
            "where o.user.id = :userId and b.id = :bookId)")
    boolean hasUserPurchasedBook(@Param("userId") Long userId, @Param("bookId") Long bookId);
    @Query(value = "select exists (select 1 from ratings where user_id = :userId and book_id = :bookId)", nativeQuery = true)
    boolean hasUserRatedBook(@Param("userId") Long userId, @Param("bookId") Long bookId);
    @Query(value = "select exists (select 1 from reviews where user_id = :userId and book_id = :bookId)", nativeQuery = true)
    boolean hasUserReviewedBook(@Param("userId") Long userId, @Param("bookId") Long bookId);
}
