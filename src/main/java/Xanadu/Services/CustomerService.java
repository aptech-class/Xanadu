package Xanadu.Services;

import Xanadu.Entities.Customer;
import Xanadu.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
    public void save (Customer customer){
        customerRepository.save(customer);
    }
}
