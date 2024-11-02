package com.mega.e_commerce_system.Modules.order.Service;


import com.mega.e_commerce_system.Modules.order.Entities.OrderLine;
import com.mega.e_commerce_system.Modules.order.Payload.OrderLineRequest;
import com.mega.e_commerce_system.Modules.order.Payload.OrderLineResponse;

public interface OrderLineService {

    public OrderLine saveOrderLine(OrderLineRequest orderLineRequest);

    OrderLineResponse getOrderLineByOrderId(Integer orderId);
}
