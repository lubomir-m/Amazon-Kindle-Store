package org.example.ebookstore.services.implementations;

import org.example.ebookstore.entities.Currency;
import org.example.ebookstore.repositories.CurrencyRepository;
import org.example.ebookstore.services.interfaces.CurrencyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepository currencyRepository;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public List<Currency> getAllCurrencies() {
        return this.currencyRepository.findAll();
    }
}
