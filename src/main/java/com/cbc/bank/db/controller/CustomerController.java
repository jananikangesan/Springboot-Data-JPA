package com.cbc.bank.db.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cbc.bank.db.entity.Customer;
import com.cbc.bank.db.repository.CustomerRepository;

@RestController
@RequestMapping("customer")
@CrossOrigin(origins = "http://localhost:4200/")
public class CustomerController {

	@Autowired
	CustomerRepository customerRepository;
	
	@GetMapping("all")
	List<Customer> ShowAll(){
		return customerRepository.findAll();
	}
	
	@GetMapping("find/{id}")
	Customer findByCustomer(@PathVariable("id") int id) {
		Optional<Customer> opt =customerRepository.findById(id);
		if(opt.isPresent()) {
			return opt.get();
		}
		return null;
	}
	@GetMapping("findEmail/{email}")
	Optional<Customer> findByEmail(@PathVariable("email") String email){
		return customerRepository.findByEmail(email);
	}
	@GetMapping("findAll/{name}")
	List<Customer> findByAllName(@PathVariable("name") String name){
		return customerRepository.findAllByName(name);
	}
	@GetMapping("findSalary/{amount1}/{amount2}")
	List<Customer> findAllBySalary(@PathVariable("amount1") float amount1,@PathVariable("amount2") float amount2){
		return customerRepository.findAllBySalaryBetween(amount1, amount2);
	}
	@GetMapping("findAllBy/{id}/{email}")
	Customer findAllIdAndEmail(@PathVariable("id") int id,@PathVariable ("email") String email) {
		return customerRepository.findAllByIdAndEmail(id, email);
		
	}
	@DeleteMapping(path="deleteCustomer/{id}")
	Optional<Customer> deleteCustomer(@PathVariable("id") int id) {
		Optional<Customer> cust=customerRepository.findById(id);
		
		if(cust.isPresent()) {
			customerRepository.deleteById(id);
			return cust;
		}
		return null;
	}
	@PutMapping(path="updateCustomer/{id}")
	Customer updateCustomer(@PathVariable ("id") int id, @RequestBody Customer customer) {
		Optional<Customer> cust=customerRepository.findById(id);
		
		if(cust.isPresent()) {
			customerRepository.save(customer);
			return customer;
		}
		return null;
	}
	@PostMapping(path="addCustomer")
	Customer saveCustomer(@RequestBody Customer customer) {
		customerRepository.save(customer);
		return customer;
	}
	@GetMapping("getSpecificColumns/{email}")
    public List<Object[]> getSpecificColumns(@PathVariable String email) {
        return customerRepository.findSpecificColumns(email);
    }
	
    @GetMapping("ShowAllCustomersUsingSort")
    List<Customer> dispalyAllCustomersUsingSorting(@RequestParam(defaultValue = "name")String name){
    	return  customerRepository.findAll(Sort.by(name).ascending());
    }
    
   @GetMapping("showAllProductsUsingPage")
    Page<Customer> dispalyAllProduuctsUsingPaging(@RequestParam(defaultValue = "0") Integer pageNo,
    		@RequestParam(defaultValue ="1")Integer pageSize,@RequestParam(defaultValue ="name") String name){
    	Pageable pageable=PageRequest.of(pageNo,pageSize,Sort.by(name).ascending());
    	return customerRepository.findAll(pageable);
    	
    }
}
