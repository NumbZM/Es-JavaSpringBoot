package com.icoding.icodingsearch.controller;

import com.google.gson.Gson;
import com.icoding.icodingsearch.config.EmployeeRepository;
import com.icoding.icodingsearch.pojo.Employee;
import com.icoding.icodingsearch.service.EmployeeService;
import com.icoding.icodingsearch.utill.CommonResult;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/es")
public class EsController {

    @Autowired
    private EmployeeRepository er;

    @Autowired
    private EmployeeService employeeService;

    //增加
    @RequestMapping(value = "/add/",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String add(@RequestBody Employee employee){
        er.save(employee);
        System.err.println(employee);
        System.err.println("add a obj");

        return "success";
    }
    //查询
    @RequestMapping(value = "/query/", method = RequestMethod.GET)
    public List<Employee> query(@RequestParam("param")String param){
        if(param == null || "".equals(param)){
            return null;
        }
        List<Employee> result = new ArrayList<>();
        Class clazz = Employee.class;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            if ("photo".equals(fieldName)) {
                continue;
            }
            QueryBuilder queryBuilder = QueryBuilders.wildcardQuery(fieldName, "*" + param + "*");
            QueryBuilder builder = QueryBuilders.matchPhraseQuery(fieldName, param);
            Iterable<Employee> employees = er.search(queryBuilder);
            Iterable<Employee> emps = er.search(builder);
            employees.forEach(result::add);
            emps.forEach(result::add);
        }

        return result.stream().distinct().collect(Collectors.toList());
    }

    //删除
    @PostMapping ("/delete/")
    public CommonResult delete(Integer id){
        CommonResult result = new CommonResult();
        try {
            employeeService.deleteEmployeeById(id);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(500);
            result.setMsg("删除失败");
            return result;
        }
    }

    //局部更新
    @RequestMapping("/update")
    public String update(){

        Employee employee=er.queryEmployeeById("1");
//        employee.setFirstName("哈哈");
        er.save(employee);

        System.err.println("update a obj");

        return "success";
    }

}