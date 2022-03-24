package ru.alidi.market.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import org.springframework.data.annotation.Immutable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@ApiModel(description = "Информация о позиции заказа содержащая информацию о заказанном товаре и его кол-ве")
@Immutable
@Getter
public class OrderPosition {

    @NotNull(message = "Позиция в заказе должна быть привязана к определенному товару")
    private final Long productId;

    @NotNull(message = "Позиция в заказе должна иметь кол-во содержащихся в ней элементов")
    @Positive(message = "Позиция в заказе должна иметь положительное кол-во элементов")
    private final Integer quantity;

    public OrderPosition(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
