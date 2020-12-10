package com.icoding.icodingsearch.controller;

import com.google.gson.Gson;
import com.icoding.icodingsearch.config.EmployeeRepository;
import com.icoding.icodingsearch.pojo.Employee;
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

    //增加
    @RequestMapping(value = "/add/",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String add(@RequestBody Employee employee){
//        System.out.println(data.num);

//        Employee employee=new Employee(param);
//        employee.setId(id);
//        employee.setNum("Y.S.K");
//        employee.setXm("~");
//        employee.setPhone("123");
//        employee.setEmail("");
        er.save(employee);
//
        System.err.println(employee);
        System.err.println("add a obj");

        return "success";
    }

    //删除
    @RequestMapping("/delete")
    public String delete(){
        Employee employee=new Employee();
//        employee.setId("1");
        er.delete(employee);

        return "success";
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
            if("photo".equals(fieldName)){
                continue;
            }
            QueryBuilder queryBuilder = QueryBuilders.wildcardQuery(fieldName, "*" + param + "*");
            Iterable<Employee> employees = er.search(queryBuilder);
            if(employees != null){
                employees.forEach(v->{
                    Optional<Employee> any = result.stream().filter(info -> info.getId().equals(v.getId())).findAny();
                    if(!any.isPresent()){
                        result.add(v);
                    }
                });
            }
        }
        return result;
    }

}