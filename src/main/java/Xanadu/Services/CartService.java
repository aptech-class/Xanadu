package Xanadu.Services;

import Xanadu.Entities.Cart;
import Xanadu.Entities.CartItem;
import Xanadu.Entities.Customer;
import Xanadu.Repositories.CartItemRepository;
import Xanadu.Repositories.CartRepository;
import Xanadu.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CartItemRepository cartItemRepository;

    @Transactional
    public Cart addToCart(CartItem cartItem,Customer customer) {
        Cart cartExists = cartRepository.findByCustomer(customer);
        Float subTotalPrice = cartItem.getQuantity() * cartItem.getVariant().getPrice();
        cartItem.setSubTotalPrice(subTotalPrice);
        if (cartExists == null) {
            Cart cart = new Cart();
            cart.setCustomer(customer);
            cart.setTotalPrice(subTotalPrice);
            Cart cartSaved = cartRepository.save(cart);
            customer.setCart(cartSaved);
            customerRepository.save(customer);
            cartItem.setCart(cartSaved);
            CartItem cartItemSaved = cartItemRepository.save(cartItem);
            List<CartItem> cartItems = new ArrayList<>();
            cartItems.add(cartItemSaved);
            cartSaved.setCartItems(cartItems);
            return cartSaved;
        } else {
            AtomicBoolean variantExisted = new AtomicBoolean(false);
            cartExists.getCartItems().forEach(cartItemExists -> {
                if (cartItemExists.getVariant().getId().equals(cartItem.getVariant().getId())) {
                    Integer newQuantity = cartItemExists.getQuantity() + cartItem.getQuantity();
                    cartItemExists.setQuantity(newQuantity);
                    float newSubTotalPrice = newQuantity * cartItemExists.getVariant().getPrice();
                    cartExists.setTotalPrice(cartExists.getTotalPrice() - cartItemExists.getSubTotalPrice() + newSubTotalPrice);
                    cartItemExists.setSubTotalPrice(newSubTotalPrice);
                    variantExisted.set(true);
                }
            });
            if (!variantExisted.get()) {
                cartItem.setCart(cartExists);
                CartItem cartItemSaved = cartItemRepository.save(cartItem);
                cartExists.getCartItems().add(cartItemSaved);
                cartExists.setTotalPrice(cartExists.getTotalPrice()+cartItem.getSubTotalPrice());
            }
            return cartRepository.save(cartExists);
        }
    }

    public  Cart  save(Cart cart) {
        return cartRepository.save(cart);
    }
}
