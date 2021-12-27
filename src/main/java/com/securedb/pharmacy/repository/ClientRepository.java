package com.securedb.pharmacy.repository;

import com.securedb.pharmacy.entity.impl.Client;

import java.util.List;

public interface ClientRepository {

    List<Client> getAll();

    Client getById(List id);

    Client getByName(String name);

    void saveClient(Client client);

    void deleteClient(Client client);
}
