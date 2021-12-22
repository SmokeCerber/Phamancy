package com.securedb.pharmacy.repository;

import com.securedb.pharmacy.entity.impl.Client;
import com.securedb.pharmacy.entity.impl.Drug;
import com.securedb.pharmacy.entity.impl.Order;

import java.util.List;

public interface OrderRepository {

    List<Order> getAll();

    Order getById(Long id);

    Order getByOrderNumber(Integer orderNumber);

    List<Order> getByClient(Client client);

    List<Order> getByClientName(String name);

    void saveOrder(Order order);

    void deleteOrder(Order order);

    List<Order> getByDrug(Drug drug);
}
