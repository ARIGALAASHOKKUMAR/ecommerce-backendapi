package com.demo.test.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopeaseRepository extends JpaRepository<com.demo.test.entity.MyEntity, Long> {

	@Query(nativeQuery = true, value = """
						SELECT
			    p.product_id _id,
			    p.name,
			    p.description,  
			    p.price,
			    p.category,
			    p.sub_category,
			    p.bestseller,
			    p.date,
			    array_agg(DISTINCT pi.image_url) AS image,
			    array_agg(DISTINCT ps.size) AS sizes
			FROM products p
			LEFT JOIN product_images pi ON p.product_id = pi.product_id
			LEFT JOIN product_sizes ps ON p.product_id = ps.product_id
			GROUP BY p.product_id, p.name, p.description, p.price, p.category, p.sub_category, p.bestseller, p.date
			ORDER BY p.date DESC;
						""")
	List<Map<String, Object>> test();

}
