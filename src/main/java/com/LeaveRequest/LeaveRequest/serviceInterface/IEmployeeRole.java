/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LeaveRequest.LeaveRequest.serviceInterface;

import com.LeaveRequest.LeaveRequest.entities.EmployeeRole;

/**
 *
 * @author Panji Sadewo
 */
public interface IEmployeeRole {
    Iterable<EmployeeRole> findEmployeeById(String id);
    
    
}
