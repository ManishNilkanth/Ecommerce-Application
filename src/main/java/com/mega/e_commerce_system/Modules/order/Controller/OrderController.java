package com.mega.e_commerce_system.Modules.order.Controller;

import com.mega.e_commerce_system.Modules.order.Payload.OrderRequest;
import com.mega.e_commerce_system.Modules.order.Payload.OrderResponse;
import com.mega.e_commerce_system.Modules.order.Service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Integer> createOrder(@RequestBody @Valid OrderRequest request)
    {
        Integer orderId = orderService.createOrder(request);
        return new ResponseEntity<>(orderId, HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderByOderId(@PathVariable Integer orderId)
    {
        OrderResponse response = orderService.getOrderByOrderId(orderId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders()
    {
        List<OrderResponse> responses = orderService.getAllOrders();
        return new ResponseEntity<>(responses,HttpStatus.OK);
    }


}
