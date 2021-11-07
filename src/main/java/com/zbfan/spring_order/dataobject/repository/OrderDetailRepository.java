package com.zbfan.spring_order.dataobject.repository;

import com.zbfan.spring_order.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    public List<OrderDetail> findByOrderId(String orderId);
}
