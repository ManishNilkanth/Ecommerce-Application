package com.mega.e_commerce_system.Modules.order.Controller;

import com.mega.e_commerce_system.Modules.order.Payload.OrderLineResponse;
import com.mega.e_commerce_system.Modules.order.Service.OrderLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order-line")
@RequiredArgsConstructor
public class OrderLineController {

    private final OrderLineService orderLineService;

    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderLineResponse> getOrderLineByOrderId(@PathVariable Integer orderId)
    {
        OrderLineResponse response = orderLineService.getOrderLineByOrderId(orderId);
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }
}
