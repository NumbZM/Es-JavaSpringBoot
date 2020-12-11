package com.icoding.icodingsearch.service.serviceImp;

import com.icoding.icodingsearch.config.EmployeeRepository;
import com.icoding.icodingsearch.pojo.Employee;
import com.icoding.icodingsearch.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> findAll() {
        return (List<Employee>) employeeRepository.findAll();
    }

    @Override
    public void saveEmployee(Employee employee){
        employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployeeById(Integer id){
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee findById(Integer id){
        return employeeRepository.findById(id).get();
    }

}
