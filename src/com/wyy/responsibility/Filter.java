package com.wyy.responsibility;

public interface Filter {
    void doFilter(Requerst requerst, Respones respones, FilterChain chain);
}
