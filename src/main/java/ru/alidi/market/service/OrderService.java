package ru.alidi.market.service;

import ru.alidi.market.dto.OrderCalculationResponse;
import ru.alidi.market.dto.OrderCalculationRequest;
import ru.alidi.market.exception.WrongInputDataException;

public interface OrderService {

    /**
     * Расчет стоимости в разрезе всего заказа и по каждой отдельной позиции
     * @param request данные о позициях заказа, типе оплаты и адресе покупателя
     * @return данные о стоимости всего заказа и по каждой отдельной позиции
     * @throws WrongInputDataException возникает, когда не удается найти товар в кэше и стороннем сервисе
     */
    OrderCalculationResponse calculateOrder(OrderCalculationRequest request) throws WrongInputDataException;
}
