package Xanadu.Repositories;

import Xanadu.Entities.CustomerTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerTagRepository extends JpaRepository<CustomerTag,Long> {
}
