package Xanadu.Repositories;

import Xanadu.Entities.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<Vendor,Long> {
    Vendor findByName(String name);
}
