package com.securedb.pharmacy.repository;

import com.securedb.pharmacy.entity.impl.Drug;

import java.util.List;

public interface DrugRepository {

    Drug getById(Long id);

    Drug getByName(String name);

    void saveDrug(Drug drug);

    void deleteDrug(Drug drug);

    List<Drug> getAll();
}
