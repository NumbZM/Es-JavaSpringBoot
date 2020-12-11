package com.icoding.icodingsearch.config;

import com.icoding.icodingsearch.pojo.Employee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface EmployeeRepository extends ElasticsearchRepository<Employee,Integer> {
    Employee queryEmployeeById(String id);
    Employee queryEmployeeByNum(String num);
}