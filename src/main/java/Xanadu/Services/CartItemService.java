package Xanadu.Services;

import Xanadu.Entities.Cart;
import Xanadu.Entities.CartItem;
import Xanadu.Entities.Customer;
import Xanadu.Repositories.CartItemRepository;
import Xanadu.Repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class CartItemService {
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    CartRepository cartRepository;

    @Transactional
    public CartItem save(CartItem cartItem, Customer customer) {
        Cart cart = cartRepository.findByCustomer(customer);
        CartItem cartItemExists = cartItemRepository.findById(cartItem.getId()).orElse(null);
        if (cart == null || cartItemExists == null || !Objects.equals(cartItemExists.getCart().getId(), cart.getId())) {
            return null;
        }
        cart.setTotalPrice(cart.getTotalPrice()-cartItemExists.getSubTotalPrice());
        cartItemExists.setQuantity(cartItem.getQuantity());
        cartItemExists.setSubTotalPrice(cartItemExists.getVariant().getPrice() * cartItem.getQuantity());
        cart.setTotalPrice(cart.getTotalPrice()+cartItemExists.getSubTotalPrice());
        cartRepository.save(cart);
        return cartItemRepository.save(cartItemExists);
    }

    public CartItem findById(Long id) {
        return cartItemRepository.findById(id).orElse(null);
    }

    public void delete(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }
}
