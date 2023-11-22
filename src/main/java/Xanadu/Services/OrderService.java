package Xanadu.Services;

import Xanadu.Entities.*;
import Xanadu.Exceptions.OutOfStockException;
import Xanadu.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ShippingAddressRepository shippingAddressRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    VariantRepository variantRepository;
    @Autowired
    CartItemRepository cartItemRepository;

    @Transactional(readOnly = true)
    public Page<Order> findAll(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return orderRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Order findById(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        return optionalOrder.orElse(null);
    }

    @Transactional(readOnly = true)
    public Order findByIdWithOrderItems(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.getOrderItems().forEach(orderItem -> orderItem.getVariant().getProduct().getImages().forEach(EntityBasic::getId));
            return order;
        }
        return null;
    }

    @Transactional(rollbackFor = {RuntimeException.class, OutOfStockException.class})
    public void save(Customer customer, ShippingAddress shippingAddress, String note) throws OutOfStockException {
        shippingAddress.setCustomer(customer);
        ShippingAddress shippingAddressSaved = processShippingAddress(shippingAddress);

        Cart cart = cartRepository.findByCustomer(customer);
        Order order = new Order();
        order.setShippingAddress(shippingAddressSaved);
        order.setTotalPrice(cart.getTotalPrice());
        order.setOrderType(OrderType.CREATED_BY_CUSTOMER);
        order.setNote(note);
        order.setCustomer(customer);
        order.setAmount(cart.getTotalPrice());
        order.setFulfillmentStatus(FulfillmentStatus.PENDING);
        order.setShippingFee(0f);
        Order orderSaved = orderRepository.save(order);
        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            Variant variant = cartItem.getVariant();
            Integer quantity = cartItem.getQuantity();
            if (quantity > variant.getInventory()) {
                throw new OutOfStockException(variant.getProduct().getTitle() + ":" + variant.getTitle(), quantity, variant.getInventory());
            }
            variant.setInventory(quantity);
            orderItem.setVariant(variant);
            orderItem.setOrder(orderSaved);
            orderItem.setQuantity(quantity);
            orderItem.setSubTotalPrice(cartItem.getSubTotalPrice());
            orderItemRepository.save(orderItem);
            variantRepository.save(variant);
        }

        cart.setTotalPrice(0f);
        cartRepository.save(cart);
        cartItemRepository.deleteAllInBatch(cart.getCartItems());

    }

    @Transactional(readOnly = true)
    public Order findByIdWithOrderItemsAndTransactions(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.getOrderItems().forEach(orderItem -> orderItem.getVariant().getProduct().getImages().forEach(EntityBasic::getId));
            order.getTransactions().forEach(EntityBasic::getId);
            return order;
        }
        return null;
    }

    @Transactional
    public void save(Order order, Customer customer) {
        ShippingAddress shippingAddress = order.getShippingAddress();
        ShippingAddress shippingAddressSaved = processShippingAddress(shippingAddress);
        Order orderSaved = orderRepository.findById(order.getId()).orElse(null);
        assert orderSaved != null;
        orderSaved.setNote(order.getNote());
        orderSaved.setAmount(order.getAmount());
        orderSaved.setShippingFee(order.getShippingFee());
        orderSaved.setShippingAddress(shippingAddressSaved);
        orderSaved.setPaymentStatus(order.getPaymentStatus());
        orderSaved.setFulfillmentStatus(order.getFulfillmentStatus());
        orderRepository.save(orderSaved);


    }

    private ShippingAddress processShippingAddress(ShippingAddress shippingAddress) {
        if (shippingAddress.getId() == null) {
            return shippingAddressRepository.save(shippingAddress);
        } else {
            ShippingAddress shippingAddressSaved = shippingAddressRepository.findById(shippingAddress.getId()).orElse(null);
            if (shippingAddressSaved != null && !shippingAddressSaved.equals(shippingAddress)) {
                shippingAddress.setId(null);
                return shippingAddressRepository.save(shippingAddress);
            } else {
                return shippingAddressSaved;
            }
        }
    }

    @Transactional(readOnly = true)
    public List<Order> findByCustomer(Customer customer) {
        return orderRepository.findByCustomer(customer);
    }
}
