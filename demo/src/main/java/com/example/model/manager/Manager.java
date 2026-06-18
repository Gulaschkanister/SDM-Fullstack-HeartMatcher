package com.example.model.manager;

import java.util.ArrayList;
import java.util.List;

public class Manager<T> implements IManager<T>{
    private List<T> managedList;

    public Manager() {
        this.managedList = new ArrayList<>();
    }

    public void add(T element) {
        managedList.add(element);
    }
    public void addAll(List<T> elementList){
        managedList.addAll(elementList);
    }
    public List<T> getAllManagedElements(){
        return this.managedList;
    }
}
