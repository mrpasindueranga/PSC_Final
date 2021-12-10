package com.psc.psc_management.Services.Interfaces;

import com.psc.psc_management.Models.Branches;
import com.psc.psc_management.Models.Employees;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface EmployeeService extends CrudRepository<Employees, Integer> {

    @Query("select e from Employees e where e.employeeName = :employeeName")
    public Employees getEmployeesByEmployeeName(@Param("employeeName") String employeeName);

    long countByBranch(Branches branches);

    Iterable<Employees> findAllByBranch(Branches branches);
}
