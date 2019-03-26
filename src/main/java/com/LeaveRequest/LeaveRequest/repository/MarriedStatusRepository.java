/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LeaveRequest.LeaveRequest.repository;

import com.LeaveRequest.LeaveRequest.entities.MarriedStatus;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author acer
 */
public interface MarriedStatusRepository extends CrudRepository<MarriedStatus, String> {
    
}
