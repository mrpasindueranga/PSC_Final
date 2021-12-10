package com.psc.psc_management.Services.Interfaces;

import com.psc.psc_management.Models.Branches;
import com.psc.psc_management.Models.Farmers;

import org.springframework.data.repository.CrudRepository;


public interface FarmerService extends CrudRepository<Farmers, Integer> {
    Iterable<Farmers> findAllByBranch(Branches branches);
}
