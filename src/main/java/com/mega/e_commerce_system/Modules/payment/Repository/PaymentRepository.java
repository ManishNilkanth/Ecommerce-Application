package com.mega.e_commerce_system.Modules.payment.Repository;

import com.mega.e_commerce_system.Modules.payment.Entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Integer> {
    Payment findByOrderId(Integer orderId);
}
