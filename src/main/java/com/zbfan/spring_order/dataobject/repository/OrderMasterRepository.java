package com.zbfan.spring_order.dataobject.repository;

import com.zbfan.spring_order.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

    public Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
