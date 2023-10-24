package Xanadu.Services;

import Xanadu.Entities.EntityBasic;
import Xanadu.Entities.Order;
import Xanadu.Entities.OrderItem;
import Xanadu.Repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

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
    public Order findByIdWithOrderItem(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.getOrderItems().forEach(OrderItem::getId);
            return order;
        }
        return null;
    }
}
