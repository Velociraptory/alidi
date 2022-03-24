package ru.alidi.market.service;

import ru.alidi.market.entity.Product;

import java.util.Optional;

public interface DataProviderService {

    Optional<Product> getProductById(Long id);
}
