package Xanadu.Repositories;

import Xanadu.Entities.VoucherItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherItemRepository extends JpaRepository<VoucherItem,Long> {
}
