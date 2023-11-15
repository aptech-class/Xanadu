package Xanadu.Controllers.Customer.Api;

import Xanadu.Entities.Cart;
import Xanadu.Entities.CartItem;
import Xanadu.Entities.Customer;
import Xanadu.Services.CartItemService;
import Xanadu.Services.CartService;
import Xanadu.Services.CustomerService;
import Xanadu.Utils.HibernateProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class ResCartController {
    @Autowired
    CartItemService cartItemService;
    @Autowired
    CustomerService customerService;
    @Autowired
    CartService cartService;

    @Autowired
    HibernateProcessor hibernateProcessor;

    @PostMapping(value = "/cartItems.json")
    public ResponseEntity<Cart> addToCart(
            @RequestBody CartItem cartItem,
            @AuthenticationPrincipal UserDetails userDetails
    ) throws InvocationTargetException, IllegalAccessException {
        Customer customer = customerService.findByUsername(userDetails.getUsername());
        Cart cart = cartService.addToCart(cartItem, customer);
        return ResponseEntity.status(200).body(hibernateProcessor.unProxy(cart, new HashMap<>(), new StringBuilder()));
    }

    @PutMapping("/cartItems/{id}.json")
    public ResponseEntity<CartItem> editCartItem(
            @PathVariable("id") Long id,
            @RequestBody CartItem cartItem,
            @AuthenticationPrincipal UserDetails userDetails
    ) throws InvocationTargetException, IllegalAccessException {
        cartItem.setId(id);
        Customer customer = customerService.findByUsername(userDetails.getUsername());
        CartItem cartItemSaved = cartItemService.save(cartItem, customer);
        return ResponseEntity.status(200).body(hibernateProcessor.unProxy(cartItemSaved, new HashMap<>(), new StringBuilder()));
    }

    @DeleteMapping("/cartItems/{id}.json")
    public ResponseEntity<Object> deleteCartItem(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        CartItem  cartItem = cartItemService.findById(id);
        Map<String, String> response = new HashMap<>();
        if(cartItem==null){
            response.put("message","error");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        if(!Objects.equals(cartItem.getCart().getCustomer().getUsername(), userDetails.getUsername())){
            response.put("message","error");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        cartItemService.delete(cartItem);
        Cart cart  = cartItem.getCart();
        cart.setTotalPrice(cart.getTotalPrice()-cartItem.getSubTotalPrice());
        cartService.save(cart);
        response.put("message", "success");
        return ResponseEntity.status(200).body(response);
    }
}
