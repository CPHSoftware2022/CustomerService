package com.example.demo.controller;

import com.example.demo.assemblers.CustomerDTOAssembler;
import com.example.demo.dto.CustomerDTO;
import com.example.demo.entity.CustomerEntity;
import com.example.demo.kafka.ProducerService;
import com.example.demo.model.EventModel;
import com.example.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    private CustomerDTOAssembler customerDTOAssembler;

    @Autowired
    private ProducerService service;

    @GetMapping("/test")
    String test() {
        service.sendMessage("test");
        return "test";
    }

    @GetMapping("/api/customers")
    public ResponseEntity<CollectionModel<CustomerDTO>> getAllCustomers()
    {
        List<CustomerEntity> customerEntities = (List<CustomerEntity>) customerRepository.findAll();

        ResponseEntity response = new ResponseEntity<>(
                customerDTOAssembler.toCollectionModel(customerEntities),
                HttpStatus.OK);

        String resultString = "CustomerDTOList{size="+customerEntities.size()+"}";

        EventModel eventModel = new EventModel("GET", response.getStatusCode(), resultString);
        service.sendMessage(eventModel.toString());

        return response;
    }

    @GetMapping("/api/customer/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable("id") Long id)
    {
        Optional<CustomerEntity> customerEntityOptional = customerRepository.findById(id);

        ResponseEntity<CustomerDTO> responseEntity = getReponseEntity(customerEntityOptional);

        sendKafkaMessage(customerEntityOptional, responseEntity);

        return responseEntity;
    }

    public void sendKafkaMessage(Optional<CustomerEntity> customerEntityOptional, ResponseEntity<CustomerDTO> responseEntity){
        if (customerEntityOptional.isPresent()){
            CustomerEntity customerEntity = customerEntityOptional.get();
            EventModel eventModel = new EventModel("GET", responseEntity.getStatusCode(), customerEntity.toString());
            service.sendMessage(eventModel.toString());
        } else {
            CustomerEntity customerEntity = new CustomerEntity();
            EventModel eventModel = new EventModel("GET", responseEntity.getStatusCode(), customerEntity.toString());
            service.sendMessage(eventModel.toString());
        }
    }

    public ResponseEntity<CustomerDTO> getReponseEntity(Optional<CustomerEntity> customerEntityOptional){
        return customerEntityOptional
                .map(customerDTOAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
