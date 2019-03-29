/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LeaveRequest.LeaveRequest.repository;

import com.LeaveRequest.LeaveRequest.entities.EmployeeRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Panji Sadewo
 */
@Repository
public interface EmployeeRoleRepository extends CrudRepository<EmployeeRole, String>{
    
    @Query(value = "select * from tb_t_employee_role where EMPLOYEE = ?1", nativeQuery = true)
    public Iterable<EmployeeRole> findEmployeeById(String id);
    
}
