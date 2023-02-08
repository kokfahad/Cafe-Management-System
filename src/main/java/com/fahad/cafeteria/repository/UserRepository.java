package com.fahad.cafeteria.repository;

import com.fahad.cafeteria.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(@Param("email") String email);
}
