package com.securedb.pharmacy;

import com.securedb.pharmacy.entity.impl.Client;
import com.securedb.pharmacy.entity.impl.Order;
import com.securedb.pharmacy.repository.OrderRepository;
import com.securedb.pharmacy.repository.impl.OrderRepositoryImpl;
import org.hibernate.Session;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class ConnectionTest {

    @Test
    public void initDB() {
        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("pharmacy-persistence-unit");
        EntityManager manager = factory.createEntityManager();

//        Client client = new Client("Ueban", "Lexa", "+7999999999");
//        Drug dr1 = new Drug("SomeDrug", 123.05, 10, new Date(), new Date(), Type.TYPE_1, Desease.DESEASE_1);
//        Drug dr2 = new Drug("SomeDrug1", 50.05, 20, new Date(), new Date(), Type.TYPE_2, Desease.DESEASE_2);
//        Order order = new Order(228, Arrays.asList(dr1, dr2), client);
//
        OrderRepository repo = new OrderRepositoryImpl(manager.unwrap(Session.class));
//
//        repo.saveOrder(order);

        Client client = manager.find(Client.class, 10L);
        List<Order> orders = repo.getByClient(client);
        orders.isEmpty();
    }
}
