/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LeaveRequest.LeaveRequest.serviceInterface;

import com.LeaveRequest.LeaveRequest.entities.Request;

/**
 *
 * @author acer
 */
public interface IRequestDAO {
    Iterable<Request> findAll();
    Request saveRequest(Request request);
    void deleteRequestById(String id);
  
    
}
