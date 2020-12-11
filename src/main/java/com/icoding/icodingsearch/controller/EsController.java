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
import java.util.*;
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
    public CommonResult add(@RequestBody Employee employee){
        CommonResult result = new CommonResult();
        try{
            er.save(employee);
            System.err.println(employee);
            System.err.println("add a obj");
            result.setMsg("新增成功！");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(500);
            result.setMsg("新增失败");
            return result;
        }
    }

    //列表
    @GetMapping("/list/")
    public CommonResult list() {
        CommonResult result = new CommonResult();
        try{
            List<Employee> list_all = employeeService.findAll();
            result.setData(list_all);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(500);
            result.setMsg("获取列表失败");
            return result;
        }
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

    //根据id搜索
    @GetMapping("/get_employee/")
    public CommonResult getEmployee(String id) {
        CommonResult result = new CommonResult();
        try{
            Employee employee = employeeService.findById(id);
            result.setData(employee);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(500);
            result.setMsg("搜索成功");
            return result;
        }
    }

    //删除
    @GetMapping ("/delete/")
    public CommonResult delete(String id){
        CommonResult result = new CommonResult();
        try {
            employeeService.deleteEmployeeById(id);
            result.setMsg("删除成功！");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(500);
            result.setMsg("删除失败");
            return result;
        }
    }

    //更新
    @PutMapping("/update/")
    public CommonResult update(Employee employee){
        CommonResult result = new CommonResult();
        try {
            employeeService.saveEmployee(employee);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(500);
            result.setMsg("更新失败");
            return result;
        }
    }
}
