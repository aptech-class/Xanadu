package Xanadu.API.EntitiesFilter;

import Xanadu.API.EntitiesQuery.ResQuery;
import Xanadu.Entities.Product;

import java.util.List;

public class ProductFilter extends Filter<Product> {


    ProductFilter(ResQuery<Product> resQuery) {
        super(resQuery);
    }

    @Override
    List<Product> filter() {
        return null;
    }
}
