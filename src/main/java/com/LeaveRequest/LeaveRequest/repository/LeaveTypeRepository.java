/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LeaveRequest.LeaveRequest.repository;

import com.LeaveRequest.LeaveRequest.entities.LeaveType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author acer
 */
public interface LeaveTypeRepository extends CrudRepository<LeaveType, String> {
     @Modifying
    @Query(value = "DELETE FROM tb_m_leave_type where id = '?1'", nativeQuery = true)
    public void deleteById(String id);
    
    
    @Query(value = "select ID FROM tb_m_leave_type where type = '?1'", nativeQuery = true)
    public String findByType(String type);
}
