package com.demo.test.ServiceImpl;

import com.demo.test.Entities.ProductsIdsEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;


public interface ProductIdRepo extends JpaRepository<ProductsIdsEntity,Integer> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM product_ids WHERE product_id = :product_id AND user_id = :user_id", nativeQuery = true)
    int deleteByProductIdAndUserId(@Param("product_id") Integer product_id, @Param("user_id") String user_id);


    @Query(value = "select product_id FROM product_ids WHERE user_id = :user_id", nativeQuery = true)
    List<Integer> ProductByUserId(@Param("user_id") String user_id);
}
