package com.icoding.icodingsearch.service;

import com.icoding.icodingsearch.pojo.Employee;

import java.util.List;


public interface EmployeeService {
    List<Employee> findAll();

    void saveEmployee(Employee employee);

    void deleteEmployeeById(Integer id);

    Employee findById(Integer id);
}
