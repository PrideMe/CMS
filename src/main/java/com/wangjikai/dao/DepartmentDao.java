package com.wangjikai.dao;

import com.wangjikai.domain.Department;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by 22717 on 2017/10/9.
 * department表操作接口
 */
@Repository
public interface DepartmentDao {
    //动态查询部门
    List<Department> selectByPage(Map<String,Object> params);
    //动态计数部门
    Integer count(Map<String,Object> params);
    //查找所有部门
    List<Department> findAllDepartment();
    //根据id查找部门
    Department findById(int id);
    //根据id删除部门
    void deleteById(int id);
    //动态插入部门
    void save(Department department);
    //动态修改部门
    void update(Department department);
}
