package Xanadu.Services;

import Xanadu.Entities.Vendor;
import Xanadu.Repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorService {
    @Autowired
    private VendorRepository vendorRepository;

    public List<Vendor> saveAll(Iterable<Vendor> vendors) {
        return vendorRepository.saveAll(vendors);
    }

    public Vendor save(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    public Vendor findByName(String name) {
        return vendorRepository.findByName(name);
    }
}
