package Xanadu.Services;

import Xanadu.Entities.Customer;
import Xanadu.Repositories.CustomerRepository;
import Xanadu.Utils.FilesProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
public class CustomerService {
    private final String uploadImageDir = "/files/images/customers";
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Transactional
    public Customer save(Customer customer) {
        MultipartFile imageFile = customer.getImageFile();
        if (!imageFile.isEmpty()) {
            try {
                String image = FilesProcessor.saveFileByMultiPart(imageFile, uploadImageDir);
                customer.setImage(image);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return customerRepository.save(customer);
    }

    public Page<Customer> findAll(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return customerRepository.findAll(pageable);
    }
}
