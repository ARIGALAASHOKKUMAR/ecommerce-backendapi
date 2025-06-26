package com.demo.test.Repositories;

import com.demo.test.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UsersRepo extends JpaRepository<UserEntity,Integer> {

    @Query(value = "SELECT * FROM users WHERE user_name = :user_name AND password = :password", nativeQuery = true)
    List<UserEntity> findingUser(@Param("user_name") String user_name, @Param("password") String password);

    @Query(value = "SELECT * FROM users WHERE user_name = :user_name", nativeQuery = true)
    List<Map<String,Object>> UserExistence(@Param("user_name") String user_name);


}
