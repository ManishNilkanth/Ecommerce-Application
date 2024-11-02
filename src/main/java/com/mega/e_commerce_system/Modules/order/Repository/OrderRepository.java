package com.mega.e_commerce_system.Modules.order.Repository;

import com.mega.e_commerce_system.Modules.order.Entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
}
