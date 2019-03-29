/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LeaveRequest.LeaveRequest.serviceInterface.serviceinterfaceimpl;

import com.LeaveRequest.LeaveRequest.entities.EmployeeRole;
import com.LeaveRequest.LeaveRequest.repository.EmployeeRepository;
import com.LeaveRequest.LeaveRequest.repository.EmployeeRoleRepository;
import com.LeaveRequest.LeaveRequest.serviceInterface.IEmployeeRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Panji Sadewo
 */
@Service
public class EmployeeRoleDAO implements IEmployeeRole{
    
    @Autowired
    EmployeeRoleRepository employeeRoleRepository;

    @Override
    public Iterable<EmployeeRole> findEmployeeById(String id) {
        return employeeRoleRepository.findEmployeeById(id);
    }

    
}
