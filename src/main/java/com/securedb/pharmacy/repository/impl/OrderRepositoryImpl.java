package com.securedb.pharmacy.repository.impl;

import com.securedb.pharmacy.entity.impl.Client;
import com.securedb.pharmacy.entity.impl.Drug;
import com.securedb.pharmacy.entity.impl.Order;
import com.securedb.pharmacy.repository.OrderRepository;
import com.securedb.pharmacy.utils.TxUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.concurrent.Callable;

public class OrderRepositoryImpl implements OrderRepository {

    private Session session;

    public OrderRepositoryImpl(Session session) {
        this.session = session;
    }

    @Override
    public List<Order> getAll() {
        Criteria criteria = this.session.createCriteria(Order.class);
        criteria.setMaxResults(20);
        criteria.addOrder(org.hibernate.criterion.Order.asc("orderNumber"));
        return criteria.list();
    }

    public List<Order> getByClient(Client client) {
        Criteria criteria = this.session.createCriteria(Order.class);
        criteria.add(Restrictions.gt("client", client));
        return criteria.list();
    }

    @Override
    public List<Order> getByClientName(String name) {
        Criteria criteria = this.session.createCriteria(Order.class, "order");
        criteria.createAlias("order.client", "client");
        criteria.add(Restrictions.like("client.name", name));
        criteria.setMaxResults(20);
        criteria.addOrder(org.hibernate.criterion.Order.asc("orderNumber"));
        return criteria.list();
    }

    @Override
    public Order getById(Long id) {
        return this.session.find(Order.class, id);
    }

    @Override
    public Order getByOrderNumber(Integer orderNumber) {
        return null;
    }

    @Override
    public void saveOrder(Order order) {
        TxUtils.doInTransaction(this.session, new Callable<Object>() {
            @Override
            public Object call() {
                return session.save(order);
            }
        });
    }

    @Override
    public void deleteOrder(Order order) {
        TxUtils.doInTransaction(this.session, new Callable<Object>() {
            @Override
            public Object call() {
                session.delete(order);
                return null;
            }
        });
    }

    @Override
    public List<Order> getByDrug(Drug drug) {
        Criteria criteria = this.session.createCriteria(Order.class, "order");
        criteria.createAlias("order.drugs", "drug");
        criteria.add(Restrictions.eq("drug.id", drug.getId()));
        return criteria.list();
    }
}
