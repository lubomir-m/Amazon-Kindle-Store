package org.example.ebookstore.controllers;

import org.example.ebookstore.repositories.ShoppingCartRepository;
import org.example.ebookstore.services.interfaces.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ShoppingCartController {
   private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/users/cart")
    public String displayShoppingCartPage() {
        return "shopping-cart";
    }

    @PostMapping("/carts/{userId}/books/{bookId}")
    public ResponseEntity<?> addBookToShoppingCart(@PathVariable("bookId") Long bookId, Model model) {
        try {
            int itemCount = this.shoppingCartService.addBookToShoppingCart(bookId, model);
            return ResponseEntity.ok().body(itemCount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/users/cart/buyall")
    public ResponseEntity<?> buyAllBooksInShoppingCart(Model model) {
        try {
            this.shoppingCartService.buyAllBooksInShoppingCart(model);
            return ResponseEntity.ok().body("You have successfully purchased all books in your shopping cart. " +
                    "You can now read them in the " +
                    "Kindle app. Please visit your profile, if you would like to view your orders or the books " +
                    "that you own.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
