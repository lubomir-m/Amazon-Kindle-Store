package org.example.ebookstore.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "authors")
public class Author extends BaseEntity {
    @Column(name = "full_name", nullable = false)
    @Size(min = 5, max = 50)
    private String fullName;
    @ManyToMany(mappedBy = "authors")
    private Set<Book> books = new HashSet<>();
    @Column(columnDefinition = "TEXT")
    private String description;

    public Author() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Set<Book> getBooks() {
        return Collections.unmodifiableSet(this.books);
    }

    public void addBook(Book book) {
        this.books.add(book);
    }
    public void removeBook(Book book) {
        this.books.remove(book);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
