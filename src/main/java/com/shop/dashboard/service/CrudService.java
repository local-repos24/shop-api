package com.shop.dashboard.service;

import java.util.List;

public interface CrudService<T, K> {
    public T getAll();

    public T save(K object);

    public T getById(Long id);

    public T delete(Long id);

    public T update(K object);
}
