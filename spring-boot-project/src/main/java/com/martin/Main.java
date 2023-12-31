package com.martin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")
public class Main {

    private final CustomerRepository customerRepository;

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    static record NewCustomerRequest(
            String name,
            String email,
            Integer age
    ){}

    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest request) {
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setAge(request.age());
        customerRepository.save(customer);
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer id) {
        //No business logic at this point, only deleting

        customerRepository.deleteById(id);

    }


    record CustomerUpdateRequest(
            String name,
            String email,
            Integer age) {

    }
    @PutMapping("{customerId}")
    public void updateCustomer(
            @PathVariable("customerId") Integer id,
            @RequestBody CustomerUpdateRequest updateRequest){

        Customer customerToUpdate = customerRepository.getOne(id);
        customerToUpdate.setEmail(updateRequest.email());
        customerToUpdate.setName(updateRequest.name());
        customerToUpdate.setAge(updateRequest.age());
        customerRepository.save(customerToUpdate);
    }
}
