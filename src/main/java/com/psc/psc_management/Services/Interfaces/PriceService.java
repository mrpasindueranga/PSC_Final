package com.psc.psc_management.Services.Interfaces;

import com.psc.psc_management.Models.Prices;

import org.springframework.data.repository.CrudRepository;

public interface PriceService extends CrudRepository<Prices, Integer> {

}
