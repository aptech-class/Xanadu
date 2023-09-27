package Xanadu.Repositories;

import Xanadu.Entities.User;
import Xanadu.Entities.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher,Long> {
}
