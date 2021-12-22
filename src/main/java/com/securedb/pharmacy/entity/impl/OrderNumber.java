package com.securedb.pharmacy.entity.impl;

import javax.persistence.*;

@Entity
@SequenceGenerator(
        name = "order_number_gen",
        sequenceName = "order_number_seq", allocationSize = 10)
public class OrderNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_number_gen")
    @Column(name = "id")
    private Long number;

    public OrderNumber() {
    }

    @Override
    public String toString() {
        return this.number.toString();
    }
}
