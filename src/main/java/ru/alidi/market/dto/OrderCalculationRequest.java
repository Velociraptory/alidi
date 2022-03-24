package ru.alidi.market.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import org.springframework.data.annotation.Immutable;
import org.springframework.lang.Nullable;
import ru.alidi.market.enums.PaymentType;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ApiModel(description = "Запрос на расчет стоимости заказа")
@Immutable
@Getter
public class OrderCalculationRequest {

    @Valid
    @NotEmpty(message = "Переданный заказ должен содержать хотя бы 1 позицию")
    private final List<OrderPosition> positions;

    @NotNull(message = "Переданный заказ должен иметь определенный способ оплаты")
    private final PaymentType paymentType;

    @Nullable
    private final Long userAddressId;

    public OrderCalculationRequest(List<OrderPosition> positions, PaymentType paymentType, @Nullable Long userAddressId) {
        this.positions = Optional.ofNullable(positions).
                map(Collections::unmodifiableList).orElse(Collections.emptyList());
        this.paymentType = paymentType;
        this.userAddressId = userAddressId;
    }
}
