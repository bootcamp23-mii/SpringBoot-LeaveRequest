/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LeaveRequest.LeaveRequest.repository;

import com.LeaveRequest.LeaveRequest.entities.RequestStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author AdhityaWP
 */
@Repository
public interface RequestStatusRepository extends CrudRepository<RequestStatus, String>{
    @Modifying
    @Query(value = "DELETE FROM tb_t_request_status where id = '?1'", nativeQuery = true)
    public void deleteById(String id);
}
