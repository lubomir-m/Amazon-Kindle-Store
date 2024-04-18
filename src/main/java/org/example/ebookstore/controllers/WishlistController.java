package org.example.ebookstore.controllers;

import org.example.ebookstore.services.interfaces.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WishlistController {
    private final WishlistService wishlistService;

    @Autowired
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    //TODO: finish
    @GetMapping("/users/list")
    public String displayWishlistPage(Model model) {
        return "wishlist";
    }

    @PostMapping("/books/{bookId}/list")
    public ResponseEntity<?> addBookToWishlist(@PathVariable("bookId") Long bookId, Model model) {
        try {
            this.wishlistService.addBookToWishlist(bookId, model);
            return ResponseEntity.ok().body("The book was added to your list.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/books/{bookId}/list")
    public ResponseEntity<?> removeBookFromWishlist(@PathVariable("bookId") Long bookId, Model model) {
        try {
            this.wishlistService.removeBookFromWishlist(bookId, model);
            return ResponseEntity.ok().body("The book was removed from your list.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
