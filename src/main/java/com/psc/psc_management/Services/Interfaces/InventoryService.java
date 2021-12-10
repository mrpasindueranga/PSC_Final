package com.psc.psc_management.Services.Interfaces;

import com.psc.psc_management.Models.Inventorys;

import org.springframework.data.repository.CrudRepository;

public interface InventoryService extends CrudRepository<Inventorys, Integer> {

}
