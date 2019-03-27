/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LeaveRequest.LeaveRequest.serviceInterface;

import com.LeaveRequest.LeaveRequest.entities.Employee;
import java.sql.Blob;

/**
 *
 * @author Panji Sadewo
 */
public interface IEmployeeDAO {
    Iterable<Employee> findAllEmployee();
    Employee findById(String id);
    Employee savdeEmployee(Employee employee);
    void deleteEmployeeById(String id);
    String findLastId();
}
