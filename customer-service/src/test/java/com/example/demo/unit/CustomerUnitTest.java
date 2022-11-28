package com.example.demo.unit;

import com.example.demo.assemblers.CustomerDTOAssembler;
import com.example.demo.dto.CustomerDTO;
import com.example.demo.entity.AddressEntity;
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
import static org.mockito.Mockito.mock;

@SpringBootTest
class CustomerUnitTest {

	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	CustomerDTOAssembler customerDTOAssembler;
	@Test
	@DisplayName("Testing getting customer with id 20")
	void getCustomerWithId20(){
		System.out.println("--------------- Testing getting customer with id 20 ---------------");
		CustomerEntity customer = customerRepository.findById(Long.valueOf(20)).get();
		String exp = "Rodd Rosenkranc";
		String act = customer.getName();
		assertEquals(exp, act);
	}

	@Test
	@DisplayName("Testing customer name with id 20 does not have name")
	void getCustomerNameWithId20NegativeTest(){
		System.out.println("--------------- Testing customer with id 20 does not have name ---------------");
		CustomerEntity customer = customerRepository.findById(Long.valueOf(20)).get();
		String exp = "Katinka Carlsen";
		String act = customer.getName();
		assertNotEquals(exp, act);
	}

	@Test
	@DisplayName("Testing getting customer email with id 20")
	void getCustomerEmailWithId20(){
		System.out.println("--------------- Testing getting customer with id 20 ---------------");
		CustomerEntity customer = customerRepository.findById(Long.valueOf(20)).get();
		String exp = "rrosenkrancj@edublogs.org";
		String act = customer.getEmail();
		assertEquals(exp, act);
	}

	@Test
	@DisplayName("Testing getting cutomer that does not exist")
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
	@DisplayName("Testing length of customer list isn't 300")
	void getLengthOfAllCustomersNegativeTest(){
		System.out.println("--------------- Testing length of customer list isn't 300 ---------------");
		List<CustomerEntity> customerEntities = (List<CustomerEntity>) customerRepository.findAll();

		int exp = 300;
		int act = customerEntities.size();

		assertNotEquals(exp, act);
	}

	@Test
	@DisplayName("Testing length of customer list is 21")
	void getLengthOfAllCustomers(){
		System.out.println("--------------- Testing length of customer list is 21 ---------------");
		List<CustomerEntity> customerEntities = (List<CustomerEntity>) customerRepository.findAll();

		int exp = 21;
		int act = customerEntities.size();

		assertEquals(exp, act);
	}

	@Test
	@DisplayName("Testing getting customer address, with id 20")
	void getCustomerAddressWithId20(){
		System.out.println("--------------- Testing getting customer Address with id 20 ---------------");
		CustomerEntity customer = customerRepository.findById(Long.valueOf(20)).get();
		AddressEntity address = new AddressEntity();
		address.setCity("Leoneburgh");
		address.setStreet("792 Dusty Ports Suite 321");
		address.setPostalCode(599846124);

		AddressEntity exp = address;
		AddressEntity act = customer.getAddress();
		assertEquals(exp.getCity(), act.getCity());
		assertEquals(exp.getStreet(), act.getStreet());
		assertEquals(exp.getPostalCode(), act.getPostalCode());
	}

}
