package com.mega.e_commerce_system.Modules.order.Service.ServiceImpl;

import com.mega.e_commerce_system.Exceptions.CustomerNotFoundException;
import com.mega.e_commerce_system.Exceptions.InsufficientAmountException;
import com.mega.e_commerce_system.Exceptions.OrderNotFoundException;
import com.mega.e_commerce_system.Modules.Notification.EmailService;
import com.mega.e_commerce_system.Modules.customer.Entities.Customer;
import com.mega.e_commerce_system.Modules.customer.Repository.CustomerRepository;
import com.mega.e_commerce_system.Modules.order.Entities.OrderStatus;
import com.mega.e_commerce_system.Modules.order.Repository.OrderRepository;
import com.mega.e_commerce_system.Modules.product.Entities.Product;
import com.mega.e_commerce_system.Modules.product.Payload.ProductPurchaseResponse;
import com.mega.e_commerce_system.Modules.product.Service.ProductService;
import com.mega.e_commerce_system.Modules.order.Entities.Order;
import com.mega.e_commerce_system.Modules.order.Payload.OrderLineRequest;
import com.mega.e_commerce_system.Modules.order.Payload.OrderRequest;
import com.mega.e_commerce_system.Modules.order.Payload.OrderResponse;
import com.mega.e_commerce_system.Modules.order.Service.OrderLineService;
import com.mega.e_commerce_system.Modules.order.Service.OrderService;
import com.mega.e_commerce_system.Modules.payment.Payload.ProductPurchaseRequest;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductService productService;
    private final OrderLineService orderLineService;
    private final EmailService emailService;

    @Override
    public Integer createOrder(OrderRequest request) throws MessagingException {

        var customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(()-> new CustomerNotFoundException("Customer","customerId",request.getCustomerId()));

        var purchasedProducts = productService.purchaseProduct(request.getProducts());
        var payableAmount = calculateTotalAmount(purchasedProducts);

        if(!Objects.equals(payableAmount, request.getAmount()))
        {
            System.out.println("WARNING: YOU NEED TO PAY:"+payableAmount);
            throw new InsufficientAmountException("Insufficient amount to purchase product");
        }
        var orderReference = "TRX" + RandomStringUtils.randomAlphanumeric(8).toUpperCase();

        Order orderRequest = new Order();
        orderRequest.setReference(orderReference);
        orderRequest.setTotalAmount(request.getAmount());
        orderRequest.setPaymentMethod(request.getPaymentMethod());
        orderRequest.setCustomer(modelMapper.map(customer, Customer.class));
        orderRequest.setProducts(
                purchasedProducts.stream()
                        .map(product-> modelMapper.map(product, Product.class))
                        .toList()
        );
        orderRequest.setStatus(OrderStatus.PENDING);
        var order = orderRepository.save(orderRequest);

        for(ProductPurchaseRequest purchaseRequest: request.getProducts())
        {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.getProductId(),
                            purchaseRequest.getQuantity()
                    )
            );
        }
        emailService.orderConfirmationEmail(
                customer.getEmail(),
                customer.getFirstName(),
                order.getTotalAmount(),
                order.getReference(),
                purchasedProducts
        );
        return order.getId();
    }

    private BigDecimal calculateTotalAmount(List<ProductPurchaseResponse> purchasedProducts) {
         return purchasedProducts.stream()
                 .map(product -> product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity())))
                 .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    @Override
    public OrderResponse getOrderByOrderId(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new OrderNotFoundException("Order","orderId",orderId));

        return modelMapper.map(order,OrderResponse.class);
    }



    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(order -> modelMapper.map(order,OrderResponse.class))
                .toList();
    }





}
