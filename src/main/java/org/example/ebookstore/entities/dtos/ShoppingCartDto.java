package org.example.ebookstore.entities.dtos;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import org.example.ebookstore.entities.Book;
import org.example.ebookstore.entities.User;

import java.util.HashSet;
import java.util.Set;

public class ShoppingCartDto {
    private UserDto user;
    private Set<BookDto> books = new HashSet<>();

    public ShoppingCartDto() {
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public Set<BookDto> getBooks() {
        return books;
    }

    public void setBooks(Set<BookDto> books) {
        this.books = books;
    }
}
