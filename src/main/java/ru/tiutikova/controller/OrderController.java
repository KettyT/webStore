package ru.tiutikova.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.tiutikova.dto.ResultDto;
import ru.tiutikova.dto.SimpleDto;
import ru.tiutikova.dto.order.FullOrderInfoDto;
import ru.tiutikova.dto.order.OrderDto;
import ru.tiutikova.service.OrderService;

import java.util.List;

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

    @RequestMapping(value = "getAllOrder", method = RequestMethod.GET)
    public List<OrderDto> getAllOrder () {
        return orderService.getAllOrder();
    }

    @RequestMapping(value = "getOrderById", method = RequestMethod.POST)
    public FullOrderInfoDto getOrderById (@RequestBody SimpleDto dto) {
        return orderService.getOrderById(dto);
    }

}
