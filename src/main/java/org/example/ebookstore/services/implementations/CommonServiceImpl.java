package org.example.ebookstore.services.implementations;

import org.example.ebookstore.entities.dtos.BookDto;
import org.example.ebookstore.services.interfaces.CommonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Map;

@Service
public class CommonServiceImpl implements CommonService {

    @Override
    public void addBookPageAttributesToModel(Model model, Page<BookDto> bookDtoPage,
                                             Pageable pageable, int page, String sortBy) {
        model.addAttribute("books", bookDtoPage.getContent());
        model.addAttribute("currentPage", bookDtoPage.getNumber());
        model.addAttribute("totalPages", bookDtoPage.getTotalPages());
        model.addAttribute("currentSort", sortBy);
        model.addAttribute("numberOfBooks", bookDtoPage.getTotalElements());

        Map<String, String> sortOptions = Map.of(
                "purchaseCountDesc", "Best Sellers",
                "priceAsc", "Price: Low to High",
                "priceDesc", "Price: High to Low",
                "averageRatingDesc", "Avg. Customer Review",
                "publicationDateDesc", "Publication Date"
        );
        model.addAttribute("sortOptions", sortOptions);

        int startIndex = page * pageable.getPageSize() + 1;
        int endIndex = startIndex + pageable.getPageSize() - 1;
        if (endIndex > bookDtoPage.getTotalElements()) {
            endIndex = (int) bookDtoPage.getTotalElements();
        }
        model.addAttribute("startIndex", startIndex);
        model.addAttribute("endIndex", endIndex);
    }
}
