package Xanadu.API.EntitiesFilter;


import Xanadu.API.EntitiesQuery.ResQuery;
import Xanadu.Entities.EntityBasic;

import java.util.List;

public abstract class Filter<E extends EntityBasic> {
    protected ResQuery<E> resQuery;

    Filter(ResQuery<E> resQuery) {
        this.resQuery = resQuery;
    }

    abstract List<E> filter();
}
