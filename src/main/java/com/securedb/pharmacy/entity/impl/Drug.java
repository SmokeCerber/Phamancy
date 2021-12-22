package com.securedb.pharmacy.entity.impl;

import com.securedb.pharmacy.entity.Desease;
import com.securedb.pharmacy.entity.SqlEntity;
import com.securedb.pharmacy.entity.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "drugs")
public class Drug implements SqlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false, columnDefinition = "decimal(6,2)")
    private double price = 0.00;

    @Column(name = "quantity", nullable = false)
    private int quantity = 0;

    @Column(name = "incept_date", columnDefinition = "date")
    @Temporal(TemporalType.DATE)
    private Date inceptDate;

    @Column(name = "expire_date", columnDefinition = "date")
    @Temporal(TemporalType.DATE)
    private Date expireDate;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type", nullable = false)
    private Type type;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "desease", nullable = false)
    private Desease desease;

    public Drug() {
    }

    public Drug(String name, double price, int quantity, Date inceptDate, Date expireDate, Type type, Desease desease) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.inceptDate = inceptDate;
        this.expireDate = expireDate;
        this.type = type;
        this.desease = desease;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getInceptDate() {
        return inceptDate;
    }

    public void setInceptDate(Date inceptDate) {
        this.inceptDate = inceptDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Desease getDesease() {
        return desease;
    }

    public void setDesease(Desease desease) {
        this.desease = desease;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Drug && ((Drug) obj).id == this.id;
    }

    @Override
    public String toString() {
        return "<html>Название: ".concat(this.name).concat(", Стоимость: "+this.price)
                .concat(", Количество: "+this.quantity).concat(" шт.")
                .concat("<br>Начало: "+this.inceptDate.toString()).concat(", Конец: "+this.expireDate)
                .concat("<br>Тип: "+this.type).concat(", Недуги: "+this.desease).concat("</html>");
    }

    public Long getId() {
        return this.id;
    }
}
