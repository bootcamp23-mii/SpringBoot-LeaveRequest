/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LeaveRequest.LeaveRequest.repository;

import com.LeaveRequest.LeaveRequest.entities.Employee;
import java.sql.Blob;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Panji Sadewo
 */
@Repository
public interface EmployeeRepository extends CrudRepository<Employee, String>{
    @Modifying
    @Query(value = "DELETE FROM tb_m_employee where id = ?1", nativeQuery = true)
    public void deleteById(String id);
    
    @Query(value = "select max(ID) from tb_m_employee", nativeQuery = true)
    public String findLastId();
    
     @Query(value = "select * from tb_m_employee group by IDMANAGER", nativeQuery = true)
    public Iterable<Employee> findEmployeeById();

}
