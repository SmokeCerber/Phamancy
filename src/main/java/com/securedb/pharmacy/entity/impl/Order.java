package com.securedb.pharmacy.entity.impl;

import com.securedb.pharmacy.entity.SqlEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order implements SqlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_number", referencedColumnName = "id", nullable = false)
    private OrderNumber orderNumber;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "order_to_drugs",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "drug_id", referencedColumnName = "id"))
    private List<Drug> drugs = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
    private Client client;

    public Order() {
    }

    public Order(Client client) {
        this.client = client;
    }

    public Order(OrderNumber orderNumber, List<Drug> drugs, Client client) {
        this.orderNumber = orderNumber;
        this.drugs = drugs;
        this.client = client;
    }

    public void addDrug(Drug drug) {
        this.drugs.add(drug);
    }

    public OrderNumber getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(OrderNumber orderNumber) {
        this.orderNumber = orderNumber;
    }

    public List<Drug> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<Drug> drugs) {
        this.drugs = drugs;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Order && ((Order) obj).id == this.id;
    }

    @Override
    public String toString() {
        return "<html>Заказ №" + this.orderNumber
                + ",<br> Клиент: " + this.client.getName().concat(" ").concat(this.client.getSurname())
                + ",<br> Номер клиента: " + this.client.getPhoneNumber()
                + ",<br> Лекарств в заказе: " + this.drugs.size()
                + "</html>";
    }

    public String getInfo() {
        String header =  "<h2>Заказ №" + this.orderNumber
                + "</h2>Клиент: " + this.client.getName().concat(" ").concat(this.client.getSurname())
                + "<br>Телефон: " + this.client.getPhoneNumber()
                + "<h3>Лекарства:</h3><ul>";
        StringBuilder sb = new StringBuilder();
        for (Drug drug: this.getDrugs()) {
            sb.append("<li>Название: ").append(drug.getName())
                    .append("<br>Стоимость: ").append(drug.getPrice())
                    .append("<br>Тип: ").append(drug.getType().name())
                    .append("<br>Недуги: ").append(drug.getDesease().name()).append("</li>");
        }
        return header.concat(sb.toString()).concat("</ul>");
    }
}
