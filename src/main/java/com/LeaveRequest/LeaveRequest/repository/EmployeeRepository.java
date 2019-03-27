/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LeaveRequest.LeaveRequest.repository;

import com.LeaveRequest.LeaveRequest.entities.Employee;
import java.sql.Blob;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Panji Sadewo
 */
@Repository
public interface EmployeeRepository extends CrudRepository<Employee, String>{
    
    @Query(value = "select max(ID) from tb_m_employee", nativeQuery = true)
    public String findLastId();

}
