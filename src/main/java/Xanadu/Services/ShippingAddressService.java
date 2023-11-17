package Xanadu.Services;

import Xanadu.Entities.Customer;
import Xanadu.Entities.ShippingAddress;
import Xanadu.Repositories.CustomerRepository;
import Xanadu.Repositories.ShippingAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShippingAddressService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ShippingAddressRepository   shippingAddressRepository;
    @Transactional(readOnly = true)
    public List<ShippingAddress> findByCustomer(String username) {
        Customer customer = customerRepository.findByUsername(username);
        return shippingAddressRepository.findByCustomer(customer);
    }
}
