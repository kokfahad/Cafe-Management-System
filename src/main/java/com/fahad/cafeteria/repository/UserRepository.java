package com.fahad.cafeteria.repository;

import com.fahad.cafeteria.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

//    User findByEmail(@Param("email") String email);
    Optional<User> findByEmail(@Param("email") String email);

//    List<User> findByRole(@Param("role") String role)findByEmail;
    @Query(value = "select email from user where role =:role ", nativeQuery = true)
    List<String> findAllAdminEmails(@Param("role") String role);

}
