package com.mega.e_commerce_system.Modules.order.Service.ServiceImpl;


import com.mega.e_commerce_system.Exceptions.OrderNotFoundException;
import com.mega.e_commerce_system.Modules.order.Repository.OrderLineRepository;
import com.mega.e_commerce_system.Modules.order.Entities.Order;
import com.mega.e_commerce_system.Modules.order.Entities.OrderLine;
import com.mega.e_commerce_system.Modules.order.Payload.OrderLineRequest;
import com.mega.e_commerce_system.Modules.order.Payload.OrderLineResponse;
import com.mega.e_commerce_system.Modules.order.Repository.OrderRepository;
import com.mega.e_commerce_system.Modules.order.Service.OrderLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderLineServiceImpl implements OrderLineService {

    private final OrderLineRepository orderLineRepository;
    private final OrderRepository orderRepository;
    @Override
    public OrderLine saveOrderLine(OrderLineRequest orderLineRequest) {
        Order order = orderRepository.findById(orderLineRequest.getOrderId())
                .orElseThrow(()-> new OrderNotFoundException("Order","orderId",orderLineRequest.getOrderId()));
        OrderLine orderLine = mapToOrderLine(orderLineRequest);
        orderLine.setOrder(order);
        return orderLineRepository.save(orderLine);

    }

    private OrderLine mapToOrderLine(OrderLineRequest orderLineRequest) {
        return OrderLine.builder()
                .productId(orderLineRequest.getProductId())
                .quantity(orderLineRequest.getQuantity())
                .order(Order.builder().id(orderLineRequest.getOrderId()).build())
                .build();
    }

    @Override
    public OrderLineResponse getOrderLineByOrderId(Integer orderId) {
        OrderLine orderLine = orderLineRepository.findByOrderId(orderId);
        return mapToOrderLineResponse(orderLine);
    }

    private OrderLineResponse mapToOrderLineResponse(OrderLine orderLine) {
        return OrderLineResponse.builder()
                .id(orderLine.getId())
                .quantity(orderLine.getQuantity())
                .build();
    }
}
