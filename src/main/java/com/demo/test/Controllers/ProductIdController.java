package com.demo.test.Controllers;

import com.demo.test.Entities.ProductsIdsEntity;
import com.demo.test.ServiceImpl.ProductIdRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class ProductIdController {
    @Autowired
    ProductIdRepo repo;

    @PostMapping("/save_products")
    public ResponseEntity<?> save_products(@RequestBody ProductsIdsEntity entity){
        Map<String,Object> response = new HashMap<>();
        try{
            ProductsIdsEntity ent = new ProductsIdsEntity();
            ent.setUser_id(entity.getUser_id());
            ent.setProduct_id(entity.getProduct_id());
            repo.save(ent);
            response.put("message","product added to user"+entity.getUser_id());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message","failed to add product");
            return ResponseEntity.status(500).body(response);
        }
    }


    @PostMapping("/delete_products")
    public ResponseEntity<?> delete_products(@RequestBody ProductsIdsEntity entity){
        Map<String,Object> response = new HashMap<>();
        try{
            int length=repo.deleteByProductIdAndUserId(entity.getProduct_id(),entity.getUser_id());
            response.put("message","product deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message","failed to add product");
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/products_by_id")
    public ResponseEntity<?> products_by_id(@RequestParam String user_name){
        Map<String,Object> response = new HashMap<>();
        try{
            List<Integer> products = repo.ProductByUserId(user_name);
            response.put("message","Products get successfully");
            response.put("products",products);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message","failed to add product");
            return ResponseEntity.status(500).body(response);
        }
    }
}
