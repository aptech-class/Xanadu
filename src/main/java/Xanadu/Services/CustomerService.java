package Xanadu.Services;

import Xanadu.Entities.Customer;
import Xanadu.Entities.EntityBasic;
import Xanadu.Entities.OrderItem;
import Xanadu.Repositories.CustomerRepository;
import Xanadu.Utils.FilesProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


@Service
public class CustomerService {
    private final String uploadImageDir = "/files/images/customers";
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Transactional
    public Customer save(Customer customer) {
        if (customer.getPassword() != null && !customer.getPassword().isEmpty()) {
            customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        } else {
            Optional<Customer> customerOptionalExists = customerRepository.findById(customer.getId());
            customerOptionalExists.ifPresent(value -> customer.setPassword(value.getPassword()));
        }

        MultipartFile imageFile = customer.getImageFile();
        if (!imageFile.isEmpty()) {
            try {
                String image = customer.getImage();
                String newImage = FilesProcessor.saveFileByMultiPart(imageFile, uploadImageDir);
                customer.setImage(newImage);
                if (image != null) {
                    FilesProcessor.deleteFile(image);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return customerRepository.save(customer);
    }

    @Transactional(readOnly = true)
    public Page<Customer> findAll(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return customerRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public Customer findByUsernameWithOrders(String username) {
        Customer customer = customerRepository.findByUsername(username);
        if (customer == null) {
            return null;
        }
        customer.getOrders().forEach(order -> {
            order.getOrderItems().forEach(OrderItem::getId);
        });
        return customer;
    }


    public List<Customer> findByNameIn(String name) {
        return customerRepository.findByNameIn(name);
    }

    @Transactional(readOnly = true)
    public Customer findByUsernameWithCart(String username) {
        Customer customer = customerRepository.findByUsername(username);
        if (customer != null && customer.getCart() != null) {
            customer.getCart().getCartItems().forEach(cartItem -> cartItem.getVariant().getProduct().getImages().forEach(EntityBasic::getId));
        }
        return customer;
    }
}
