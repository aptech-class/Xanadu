package Xanadu.Repositories;

import Xanadu.Entities.Cart;
import Xanadu.Entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    CartItem findByCart(Cart cart);
}
