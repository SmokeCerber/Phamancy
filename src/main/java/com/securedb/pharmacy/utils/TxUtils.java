package com.securedb.pharmacy.utils;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.concurrent.Callable;

public abstract class TxUtils {

    public static void doInTransaction(Session session, Callable<Object> callable) {
        final Transaction tx = session.beginTransaction();
        try {
            callable.call();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
