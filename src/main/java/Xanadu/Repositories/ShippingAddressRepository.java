package Xanadu.Repositories;

import Xanadu.Entities.Customer;
import Xanadu.Entities.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress,Long> {
    List<ShippingAddress> findByCustomer(Customer customer);
}
