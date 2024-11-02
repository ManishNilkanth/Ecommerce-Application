package com.mega.e_commerce_system.Modules.payment.Service.ServiceImpl;

import com.mega.e_commerce_system.Exceptions.*;
import com.mega.e_commerce_system.Modules.customer.Entities.Customer;
import com.mega.e_commerce_system.Modules.customer.Repository.CustomerRepository;
import com.mega.e_commerce_system.Modules.order.Entities.Order;
import com.mega.e_commerce_system.Modules.order.Entities.OrderStatus;
import com.mega.e_commerce_system.Modules.order.Repository.OrderRepository;
import com.mega.e_commerce_system.Modules.payment.Entities.PaymentStatus;
import com.mega.e_commerce_system.Modules.payment.Payload.PaymentResponse;
import com.mega.e_commerce_system.Modules.payment.Repository.PaymentRepository;
import com.mega.e_commerce_system.Modules.payment.Entities.Payment;
import com.mega.e_commerce_system.Modules.payment.Payload.PaymentRequest;
import com.mega.e_commerce_system.Modules.payment.Service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository;

//    private final NotificationProducer notificationProducer;
@Override
public PaymentResponse createPayment(PaymentRequest request) {

    Payment isPaymentExist = paymentRepository.findByOrderId(request.getOrderId());

    if(isPaymentExist != null && isPaymentExist.getPaymentStatus() == PaymentStatus.COMPLETED )
    {
        throw new DuplicatePaymentException("Payment is already done for this order id");
    }

    Order order = orderRepository.findById(request.getOrderId())
            .orElseThrow(()-> new OrderNotFoundException("Order","orderId",request.getOrderId()));

    Customer customer = customerRepository.findById(request.getCustomer().getId())
            .orElseThrow(()-> new CustomerNotFoundException("Customer","customerId",request.getCustomer().getId()));

    if(!customer.getFirstName().equals(request.getCustomer().getFirstName()) ||
       !customer.getLastName().equals(request.getCustomer().getLastName()) ||
       !customer.getEmail().equals(request.getCustomer().getEmail())
    )
    {
        throw new CustomerDataNotMatchedException("Customer data does not matches with registered customer");
    }

    Payment payment = mapToPayment(request);
    payment.setPaymentStatus(PaymentStatus.COMPLETED);

    order.setPayment(payment);
    order.setStatus(OrderStatus.CONFIRM);
    payment.setOrder(order);
    paymentRepository.save(payment);
    orderRepository.save(order);

    return PaymentResponse.builder()
            .paymentId(payment.getId())
            .customerId(customer.getId())
            .orderId(order.getId())
            .paymentMethod(payment.getPaymentMethod())
            .amount(payment.getAmount())
            .paymentStatus(payment.getPaymentStatus())
            .build();
}

    private Payment mapToPayment(PaymentRequest request) {
        return Payment.builder()
                .paymentMethod(request.getPaymentMethod())
                .amount(request.getAmount())
                .build();
    }

}
