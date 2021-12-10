package com.psc.psc_management.Services.Interfaces;

import com.psc.psc_management.Models.Vehicles;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VehicleService extends CrudRepository<Vehicles, Integer> {
}
