package Xanadu.Repositories;

import Xanadu.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByUsername(String username);

    @Query("SELECT c FROM Customer c WHERE c.username LIKE %:name% OR c.firstName LIKE %:name% OR c.lastName LIKE %:name%")
    List<Customer> findByNameIn(@Param("name") String name);
}
