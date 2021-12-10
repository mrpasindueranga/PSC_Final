package com.psc.psc_management.Services.Interfaces;

import com.psc.psc_management.Models.Branches;
import com.psc.psc_management.Models.BuyPaddy;
import org.springframework.data.repository.CrudRepository;

public interface BuyPaddyService extends CrudRepository<BuyPaddy, Integer> {
    Iterable<BuyPaddy> findAllByBranch(Branches branches);
}
