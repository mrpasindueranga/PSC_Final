package com.psc.psc_management.Services.Interfaces;

import com.psc.psc_management.Models.Branches;
import org.springframework.data.repository.CrudRepository;

public interface BranchService extends CrudRepository<Branches, Integer> {

}