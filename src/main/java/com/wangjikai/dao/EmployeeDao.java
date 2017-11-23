package com.wangjikai.dao;

import com.wangjikai.domain.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by 22717 on 2017/10/24.
 * employee表操作接口
 */
@Repository
public interface EmployeeDao {
    //根据参数查询员工总数
    Integer count(Map<String,Object> params);
    //动态查询雇员
    List<Employee> findByPage(Map<String,Object> params);
    //动态插入雇员
    void save(Employee employee);
    //根据id删除雇员
    void deleteById(Integer id);
    //根据id查询雇员
    Employee findById(Integer id);
    //动态修改雇员
    void update(Employee employee);
}
