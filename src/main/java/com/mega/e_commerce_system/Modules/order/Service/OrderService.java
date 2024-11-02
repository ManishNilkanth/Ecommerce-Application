package com.mega.e_commerce_system.Modules.order.Service;


import com.mega.e_commerce_system.Modules.order.Payload.OrderRequest;
import com.mega.e_commerce_system.Modules.order.Payload.OrderResponse;

import java.util.List;

public interface OrderService {
    Integer createOrder(OrderRequest request);

    OrderResponse getOrderByOrderId(Integer orderId);

    List<OrderResponse> getAllOrders();
}
