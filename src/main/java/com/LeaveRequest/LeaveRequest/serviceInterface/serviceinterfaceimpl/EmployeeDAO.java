/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LeaveRequest.LeaveRequest.serviceInterface.serviceinterfaceimpl;

import com.LeaveRequest.LeaveRequest.entities.Employee;
import com.LeaveRequest.LeaveRequest.repository.EmployeeRepository;
import com.LeaveRequest.LeaveRequest.serviceInterface.IEmployeeDAO;
import java.sql.Blob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Panji Sadewo
 */
@Service
public class EmployeeDAO implements IEmployeeDAO{
    
    @Autowired
    EmployeeRepository er;
    

    @Override
    public Employee findById(String id) {
        return er.findById(id).get();
    }

    @Override
    public Employee savdeEmployee(Employee employee) {
        return er.save(employee);
    }

    @Override
    public Iterable<Employee> findAllEmployee() {
        return er.findAll();
    }

    @Override
    public String findLastId() {
        return er.findLastId();
    }
    
}
