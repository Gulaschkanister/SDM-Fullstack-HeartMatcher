package com.example.model.manager;

import java.util.List;

public interface IManager<T> {
    public void add(T element);
    public List<T> getAllManagedElements();
     public void addAll(List<T> elementList);
}
