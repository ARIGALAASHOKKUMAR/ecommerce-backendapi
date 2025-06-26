package com.demo.test.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="product_ids")
@Data
public class ProductsIdsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String user_id;
    private Integer product_id;

}
