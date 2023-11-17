package Xanadu.Controllers.Admin.Api;

import Xanadu.Entities.Customer;
import Xanadu.Services.CustomerService;
import Xanadu.Utils.HibernateProcessor;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/admin/api")
public class ResCustomerManager {
    @Autowired
    private CustomerService customerService;
    @Autowired
    HibernateProcessor hibernateProcessor;

    @GetMapping("/customers.json")
    @ResponseBody
    public List<Customer> getCustomer(@RequestParam("name") String name) {
        List<Customer> customers = customerService.findByNameIn(name);

        customers.parallelStream().forEach(customer -> {
            try {
                hibernateProcessor.unProxy(customer, new HashMap<>(), Customer.class.getName()+"/");
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
        return customers;
    }
}
