package org.example.ebookstore.services.interfaces;

import org.example.ebookstore.entities.Order;
import org.springframework.ui.Model;

public interface OrderService {
    Order createOrder(Long bookId, Model model);
}
