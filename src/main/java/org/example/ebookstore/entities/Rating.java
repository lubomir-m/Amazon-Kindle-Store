package org.example.ebookstore.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "ratings")
public class Rating extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "submission_date", nullable = false)
    private LocalDate submissionDate;
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    public Rating() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
