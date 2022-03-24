package ru.alidi.market.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import org.springframework.data.annotation.Immutable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@ApiModel(description = "Информация о позиции заказа содержащая информацию о заказанном товаре, его кол-ве и рассчитанную итоговую стоимость")
@Immutable
@Getter
public class CalculatedOrderPosition extends OrderPosition {

    @NotNull(message = "Рассчитанная позиция должна иметь итоговую стоимость")
    @PositiveOrZero(message = "Рассчитанная позиция должна иметь нулевую либо позитивную стоимость")
    private final BigDecimal cost;

    public CalculatedOrderPosition(Long productId, Integer quantity, BigDecimal cost) {
        super(productId, quantity);
        this.cost = cost;
    }
}
