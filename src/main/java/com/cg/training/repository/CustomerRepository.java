package com.cg.training.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cg.training.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String>{

	@Query("from Customer a where a.email=:email")
	public Customer findCustomerByEmail(@Param("email")String email);
	
	@Query("from Customer a where a.mobileNo=:mobileNo")
	public Customer findCustomerByMobile(@Param("mobileNo")long mobileNo);
	
	@Query("from Customer a where a.userName=:userName")
	public Customer findUserByCustomerUserName(@Param("userName")String userName);
}
