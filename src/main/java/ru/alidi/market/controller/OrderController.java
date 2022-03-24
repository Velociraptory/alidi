package ru.alidi.market.controller;

import org.springframework.web.bind.annotation.RequestBody;
import ru.alidi.market.controller.url.OrderUrlConstants;
import ru.alidi.market.dto.OrderCalculationResponse;
import ru.alidi.market.dto.OrderCalculationRequest;
import ru.alidi.market.dto.common.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alidi.market.exception.WrongInputDataException;
import ru.alidi.market.service.OrderService;

import javax.validation.Valid;

@RestController
@RequestMapping(OrderUrlConstants.ROOT)
@Api(tags = "Контроллер для взаимодействия с заказами")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ApiOperation(value = "Рассчитать стоимость переданного заказа")
    @PostMapping(OrderUrlConstants.CALCULATE)
    public ApiResponse<OrderCalculationResponse> calculateOrder(@RequestBody @Valid OrderCalculationRequest request) throws WrongInputDataException {
        logger.info("Расчет стоимости переданного заказа");
        return ApiResponse.success(orderService.calculateOrder(request));
    }
}
