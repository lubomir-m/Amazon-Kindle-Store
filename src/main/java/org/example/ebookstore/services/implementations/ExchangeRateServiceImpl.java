package org.example.ebookstore.services.implementations;

import org.example.ebookstore.entities.Currency;
import org.example.ebookstore.entities.ExchangeRate;
import org.example.ebookstore.repositories.CurrencyRepository;
import org.example.ebookstore.repositories.ExchangeRateRepository;
import org.example.ebookstore.services.interfaces.ExchangeRateService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {
    private final ExchangeRateRepository exchangeRateRepository;
    private final CurrencyRepository currencyRepository;

    public ExchangeRateServiceImpl(ExchangeRateRepository exchangeRateRepository, CurrencyRepository currencyRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.currencyRepository = currencyRepository;
    }

    @Override
    public Optional<BigDecimal> getLatestRate(String code) {
        Optional<Currency> optional1 = this.currencyRepository.findByCodeIgnoreCase(code);
        if (optional1.isEmpty()) {
            return Optional.empty();
        }
        Currency currency = optional1.get();
        Optional<ExchangeRate> optional2 = this.exchangeRateRepository.
                findFirstByCurrencyFromOrderByValidityDateDesc(currency);
        return optional2.map(ExchangeRate::getRate);
    }
}
