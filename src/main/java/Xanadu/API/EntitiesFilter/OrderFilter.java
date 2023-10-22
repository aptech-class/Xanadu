package Xanadu.API.EntitiesFilter;

import Xanadu.API.EntitiesQuery.ResQuery;
import Xanadu.Entities.Order;

import java.util.List;

public class OrderFilter extends Filter<Order> {
    OrderFilter(ResQuery<Order> resQuery) {
        super(resQuery);
    }
    @Override
    List<Order> filter() {
        return null;
    }
}
