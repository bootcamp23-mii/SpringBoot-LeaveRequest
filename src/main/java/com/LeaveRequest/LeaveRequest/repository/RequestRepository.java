/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LeaveRequest.LeaveRequest.repository;

import com.LeaveRequest.LeaveRequest.entities.Request;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author acer
 */
@Repository
public interface RequestRepository extends CrudRepository<Request, String> {

    @Modifying
    @Query(value = "DELETE FROM tb_m_request where id = '?1'", nativeQuery = true)
    public void deleteById(String id);

    @Query(value = "SELECT * FROM tb_t_request_status WHERE request IN (SELECT id FROM tb_m_request WHERE employee IN (SELECT id FROM tb_m_employee where id='?1'))", nativeQuery = true)
    public Iterable<Request> showById(String id);
}
