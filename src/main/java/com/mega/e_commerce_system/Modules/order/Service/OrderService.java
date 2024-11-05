package com.mega.e_commerce_system.Modules.order.Service;


import com.mega.e_commerce_system.Modules.order.Payload.OrderRequest;
import com.mega.e_commerce_system.Modules.order.Payload.OrderResponse;
import jakarta.mail.MessagingException;

import java.util.List;

public interface OrderService {
    Integer createOrder(OrderRequest request) throws MessagingException;

    OrderResponse getOrderByOrderId(Integer orderId);

    List<OrderResponse> getAllOrders();
}
