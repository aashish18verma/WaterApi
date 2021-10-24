package com.rubicon.WaterApi.controller;

import com.rubicon.WaterApi.Exception.ExceptionHandling;
import com.rubicon.WaterApi.Exception.OrderAlreadyPlacedException;
import com.rubicon.WaterApi.Exception.OrderNotFoundException;
import com.rubicon.WaterApi.model.WaterOrder;
import com.rubicon.WaterApi.service.OrderService;
import com.rubicon.WaterApi.service.ServiceImpl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/WaterOrder")
public class WaterOrderController extends ExceptionHandling {

    private OrderService orderService;

    @Autowired
    public WaterOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<WaterOrder>> getAll()  {
        List orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    @PostMapping("/placeOrder")
    public ResponseEntity<WaterOrder> placeOrder(@RequestBody WaterOrder waterOrder) throws OrderAlreadyPlacedException {
        WaterOrder NewOrder = orderService.addNewOrder(waterOrder);
        return new ResponseEntity<>(NewOrder, HttpStatus.OK);
    }

    @PostMapping("/cancelOrder/{orderId}")
    public ResponseEntity<WaterOrder> cancelOrder(@PathVariable("orderId") Integer orderId) throws OrderNotFoundException {
        WaterOrder CancelOrder = orderService.cancelOrder(orderId);
        return new ResponseEntity<>(CancelOrder, HttpStatus.OK);
    }

    @GetMapping("/statusOrder/{orderId}")
    public ResponseEntity<WaterOrder> statusOrder(@PathVariable("orderId") Integer orderId) throws OrderNotFoundException {
        WaterOrder CancelOrder = orderService.GetStatus(orderId);
        return new ResponseEntity<>(CancelOrder, HttpStatus.OK);
    }

}
