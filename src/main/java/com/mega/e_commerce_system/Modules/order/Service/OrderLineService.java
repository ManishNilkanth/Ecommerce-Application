package com.mega.e_commerce_system.Modules.order.Service;


import com.mega.e_commerce_system.Modules.order.Payload.OrderLineRequest;
import com.mega.e_commerce_system.Modules.order.Payload.OrderLineResponse;
import com.mega.e_commerce_system.Modules.order.Entities.OrderLine;

public interface OrderLineService {

    public OrderLine saveOrderLine(OrderLineRequest orderLineRequest);

}
