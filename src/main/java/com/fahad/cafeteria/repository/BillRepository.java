package com.fahad.cafeteria.repository;

import com.fahad.cafeteria.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Integer> {
    @Query(value = "select * from Bill b order by b.id desc", nativeQuery = true)
    ArrayList<Bill> getAllBills();

//    ArrayList<Bill> findByEmailOrderByIdDesc(String currentUser);

    ArrayList<Bill> findByCreatedByOrderByIdDesc(String currentUser);
}
