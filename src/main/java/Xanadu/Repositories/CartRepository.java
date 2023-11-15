package Xanadu.Repositories;

import Xanadu.Entities.Cart;
import Xanadu.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findByCustomer(Customer customer);
}
