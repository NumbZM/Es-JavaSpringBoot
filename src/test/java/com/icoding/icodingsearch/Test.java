package com.icoding.icodingsearch;

import com.icoding.icodingsearch.config.EmployeeRepository;
import com.icoding.icodingsearch.pojo.Employee;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Test {

    @Autowired
    private EmployeeRepository esRepository;

    @org.junit.Test
    public void test(){

        QueryBuilder queryBuilder = QueryBuilders.wildcardQuery("phone", "*3*");
        Iterable<Employee> search = esRepository.search(queryBuilder);
        search.forEach(x-> System.out.println(x.getId()));
    }
}
