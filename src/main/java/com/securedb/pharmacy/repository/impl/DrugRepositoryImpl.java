package com.securedb.pharmacy.repository.impl;

import com.securedb.pharmacy.entity.impl.Drug;
import com.securedb.pharmacy.repository.DrugRepository;
import com.securedb.pharmacy.utils.TxUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.concurrent.Callable;

public class DrugRepositoryImpl implements DrugRepository {

    private Session session;

    public DrugRepositoryImpl(Session session) {
        this.session = session;
    }

    @Override
    public Drug getById(Long id) {
        return this.session.find(Drug.class, id);
    }

    @Override
    public Drug getByName(String name) {
        Criteria criteria = session.createCriteria(Drug.class);
        criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        return (Drug) criteria.list().get(0);
    }

    @Override
    public void saveDrug(Drug drug) {
        TxUtils.doInTransaction(this.session, new Callable<Object>() {
            @Override
            public Object call() {
                return session.save(drug);
            }
        });
    }

    @Override
    public void deleteDrug(Drug drug) {
        TxUtils.doInTransaction(this.session, new Callable<Object>() {
            @Override
            public Object call() {
                session.delete(drug);
                return null;
            }
        });
    }

    @Override
    public List<Drug> getAll() {
        Criteria criteria = this.session.createCriteria(Drug.class);
        criteria.addOrder(Order.asc("name"));
        return criteria.list();
    }
}
