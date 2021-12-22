package com.securedb.pharmacy.repository.impl;

import com.securedb.pharmacy.entity.impl.Client;
import com.securedb.pharmacy.repository.ClientRepository;
import com.securedb.pharmacy.utils.TxUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.concurrent.Callable;

public class ClientRepositoryImpl implements ClientRepository {

    private Session session;

    public ClientRepositoryImpl(Session session) {
        this.session = session;
    }

    @Override
    public List<Client> getAll() {
        Criteria criteria = this.session.createCriteria(Client.class);
        criteria.addOrder(Order.asc("name"));
        return criteria.list();
    }

    @Override
    public Client getById(List id) {
        return this.session.find(Client.class, id);
    }

    @Override
    public Client getByName(String name) {
        Criteria criteria = this.session.createCriteria(Client.class);
        criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        return (Client) criteria.list().get(0);
    }

    @Override
    public void saveClient(Client client) {
        TxUtils.doInTransaction(session, new Callable<Object>() {
            @Override
            public Object call() {
                return session.save(client);
            }
        });
    }

    @Override
    public void deleteClient(Client client) {
        TxUtils.doInTransaction(session, new Callable<Object>() {
            @Override
            public Object call() {
                session.delete(client);
                return null;
            }
        });
    }
}
