package com.icoding.icodingsearch.service;

import com.icoding.icodingsearch.pojo.Employee;

import java.util.List;


public interface EmployeeService {
    Iterable<Employee> findAll();

    void saveEmployee(Employee employee);

    void deleteEmployeeById(String id);

    Employee findById(String id);
}
