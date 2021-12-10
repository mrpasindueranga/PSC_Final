package com.psc.psc_management.Services.Interfaces;

import com.psc.psc_management.Models.Branches;
import com.psc.psc_management.Models.IssuePaddy;

import com.psc.psc_management.Models.Vehicles;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface IssuepaddyService extends CrudRepository<IssuePaddy, Integer> {
    long countByVehicleAndDate(Vehicles vehicles, Date date);

    Iterable<IssuePaddy> findAllByBranch(Branches branches);
}
