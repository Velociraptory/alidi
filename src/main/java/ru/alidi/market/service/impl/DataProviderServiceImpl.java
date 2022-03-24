package ru.alidi.market.service.impl;

import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import ru.alidi.market.entity.Product;
import ru.alidi.market.service.DataProviderService;

import java.util.Optional;

@Service
public class DataProviderServiceImpl implements DataProviderService {

    @Override
    @CachePut("products")
    public Optional<Product> getProductById(Long id) {
        //todo реализация получения данных о товаре из стороннего микросервиса
        return Optional.empty();
    }
}
