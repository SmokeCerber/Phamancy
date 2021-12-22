package com.securedb.pharmacy.entity.impl;

import com.securedb.pharmacy.entity.SqlEntity;

import javax.persistence.*;

@Entity
@Table(name = "clients")
public class Client implements SqlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "client_name", nullable = false)
    private String name;

    @Column(name = "client_surname", nullable = false)
    private String surname;

    @Column(name = "phone", nullable = false)
    private String phoneNumber;

    public Client() {
    }

    public Client(String name, String surname, String phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Client && ((Client) obj).id == this.id;
    }

    @Override
    public String toString() {
        return "<html>Имя: ".concat(this.name).concat(",<br>Фамилия: ").concat(this.surname)
                .concat(",<br>Телефон: ").concat(this.phoneNumber).concat("</html>");
    }
}
