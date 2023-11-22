package Xanadu.Repositories;

import Xanadu.Entities.Customer;
import Xanadu.Entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByCustomer(Customer customer);
}
