package com.cbc.bank.db.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cbc.bank.db.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer>{

	Optional<Customer> findByEmail(String email);
	List<Customer> findAllByName(String name);
	
	List<Customer> findAllBySalaryBetween(float amount1, float amount2);
	
	Customer findAllByIdAndEmail(int id,String email);
	
	 @Query("SELECT c.name FROM Customer c  WHERE c.email = :email")
	 List<Object[]> findSpecificColumns(@Param("email") String email);
}
