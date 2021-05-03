package ru.tiutikova.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.tiutikova.dto.ResultDto;
import ru.tiutikova.service.OrderService;

@RestController
@RequestMapping("/api/order/")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value = "doOrder", method = RequestMethod.GET)
    public ResultDto doOrder() {
        return orderService.doOrder();
    }

}
