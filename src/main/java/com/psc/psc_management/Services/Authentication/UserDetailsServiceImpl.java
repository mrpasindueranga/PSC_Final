package com.psc.psc_management.Services.Authentication;

import com.psc.psc_management.Models.Employees;
import com.psc.psc_management.Services.Interfaces.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private EmployeeService employeeService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employees employees = employeeService.getEmployeesByEmployeeName(username);
        if (employees == null){
            throw new UsernameNotFoundException("Could Not Find User");
        }

        return new MyUserDetails(employees);
    }
}
