package com.mega.e_commerce_system.Modules.order.Repository;
import com.mega.e_commerce_system.Modules.order.Entities.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine,Integer> {
    OrderLine findByOrderId(Integer orderId);
}
