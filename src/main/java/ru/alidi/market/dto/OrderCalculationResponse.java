package ru.alidi.market.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import org.springframework.data.annotation.Immutable;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ApiModel(description = "Ответ с рассчитанной стоимостью заказа")
@Immutable
@Getter
public class OrderCalculationResponse {

    @Valid
    @NotEmpty(message = "Рассчитанный заказ должен содержать хотя бы 1 позицию")
    private final List<CalculatedOrderPosition> calculatedPositions;

    @NotNull(message = "Рассчитанный заказ должен иметь итоговую стоимость")
    @PositiveOrZero(message = "Рассчитанный заказ должен иметь нулевую либо позитивную стоимость")
    private final BigDecimal cost;

    public OrderCalculationResponse(List<CalculatedOrderPosition> calculatedPositions, BigDecimal cost) {
        this.calculatedPositions = Optional.ofNullable(calculatedPositions).
                map(Collections::unmodifiableList).orElse(Collections.emptyList());
        this.cost = cost;
    }
}
