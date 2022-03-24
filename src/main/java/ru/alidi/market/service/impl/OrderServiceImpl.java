package ru.alidi.market.service.impl;

import org.springframework.stereotype.Service;
import ru.alidi.market.dto.CalculatedOrderPosition;
import ru.alidi.market.dto.OrderCalculationRequest;
import ru.alidi.market.dto.OrderCalculationResponse;
import ru.alidi.market.dto.OrderPosition;
import ru.alidi.market.entity.Product;
import ru.alidi.market.exception.WrongInputDataException;
import ru.alidi.market.repository.ProductRepository;
import ru.alidi.market.service.DataProviderService;
import ru.alidi.market.service.OrderService;
import ru.alidi.market.util.NumberUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final ProductRepository productRepository;

    private final DataProviderService dataProviderService;

    public OrderServiceImpl(ProductRepository productRepository, DataProviderService dataProviderService) {
        this.productRepository = productRepository;
        this.dataProviderService = dataProviderService;
    }

    @Override
    public OrderCalculationResponse calculateOrder(final OrderCalculationRequest request) throws WrongInputDataException {
        final List<CalculatedOrderPosition> calculatedPositions = new ArrayList<>(request.getPositions().size());
        for (final OrderPosition position : request.getPositions()) {
            final Product product;
            //Поиск товара в кэше
            final Optional<Product> retrievingResult;
            retrievingResult = productRepository.findById(position.getProductId());
            //В зависимости от наличия товара в кэше берем его из него или идем в сторонний источник
            product = retrievingResult.isPresent() ? retrievingResult.get() :
                    dataProviderService.getProductById(position.getProductId()).
                            orElseThrow(() -> new WrongInputDataException("Не найден товар с id = " + position.getProductId()));
            calculatedPositions.add(new CalculatedOrderPosition(position.getProductId(), position.getQuantity(),
                    calculatePositionCost(product.getPrice(), position.getQuantity())));
        }
        return new OrderCalculationResponse(calculatedPositions, calculateTotalPositionsCost(calculatedPositions));
    }

    /**
     * Подсчет итоговой стоимости отдельной позиции заказа
     *
     * @param price    стоимость выбранного товара
     * @param quantity кол-во выбранного товара в заказе
     * @return итоговая стоимость позиции в заказе
     */
    private BigDecimal calculatePositionCost(final BigDecimal price, final int quantity) {
        return price.multiply(BigDecimal.valueOf(quantity), NumberUtil.getDefaultCalculationContext());
    }

    /**
     * Подсчет итоговой стоимости всего заказа
     *
     * @param calculatedPositions список позиций заказа
     * @return итоговая стоимость заказа
     */
    private BigDecimal calculateTotalPositionsCost(final List<CalculatedOrderPosition> calculatedPositions) {
        return calculatedPositions.stream()
                .map(CalculatedOrderPosition::getCost)
                .reduce(BigDecimal.ZERO, (position1, position2) -> position1.
                        add(position2, NumberUtil.getDefaultCalculationContext()));
    }
}
