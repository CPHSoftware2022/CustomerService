package com.example.demo.unit;

import com.example.demo.assemblers.CustomerDTOAssembler;
import com.example.demo.dto.CustomerDTO;
import com.example.demo.entity.CustomerEntity;
import com.example.demo.repository.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class CustomerUnitTest {

	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	CustomerDTOAssembler customerDTOAssembler;

	@Test
	void getCustomerWithId20(){
		System.out.println("--------------- Testing getting customer with id 20 ---------------");
		CustomerEntity customer = customerRepository.findById(Long.valueOf(20)).get();
		String exp = "Rodd Rosenkranc";
		String act = customer.getName();
		assertEquals(exp, act);
	}

	@Test
	void getCustomerWithId20NegativeTest(){
		System.out.println("--------------- Testing customer with id 20 does not have name ---------------");
		CustomerEntity customer = customerRepository.findById(Long.valueOf(20)).get();
		String exp = "Katinka Carlsen";
		String act = customer.getName();
		assertNotEquals(exp, act);
	}

	@Test
	void tryToGetCustomerThatDoesNotExist(){
		System.out.println("--------------- Testing getting cutomer that does not exist ---------------");
		ResponseEntity<CustomerDTO> customer = customerRepository.findById(Long.valueOf(200))
				.map(customerDTOAssembler::toModel)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());

		HttpStatus exp = HttpStatus.valueOf(404);
		HttpStatus act = customer.getStatusCode();

		assertEquals(exp, act);
	}

	@Test
	void getLengthOfAllCustomersNegativeTest(){
		System.out.println("--------------- Testing length of customer list isn't 300 ---------------");
		List<CustomerEntity> customerEntities = (List<CustomerEntity>) customerRepository.findAll();

		int exp = 300;
		int act = customerEntities.size();

		assertNotEquals(exp, act);
	}

	@Test
	void getLengthOfAllCustomers(){
		System.out.println("--------------- Testing length of customer list is 21 ---------------");
		List<CustomerEntity> customerEntities = (List<CustomerEntity>) customerRepository.findAll();

		int exp = 21;
		int act = customerEntities.size();

		assertEquals(exp, act);
	}

}
