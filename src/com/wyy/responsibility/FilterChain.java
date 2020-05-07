package com.wyy.responsibility;


import java.util.ArrayList;
import java.util.List;

public class FilterChain implements Filter {
    List<Filter> filters = new ArrayList<Filter>();
    int index = 0;

    FilterChain addFilter(Filter filter) {
        filters.add(filter);
        return this;
    }

    @Override
    public void doFilter(Requerst requerst, Respones respones, FilterChain chain) {
        if (index == filters.size()) {
            return;
        }

        Filter f = filters.get(index);
        index++;
        f.doFilter(requerst, respones, chain);
    }


}
