/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LeaveRequest.LeaveRequest.serviceInterface.serviceinterfaceimpl;

import com.LeaveRequest.LeaveRequest.entities.MarriedStatus;
import com.LeaveRequest.LeaveRequest.repository.MarriedStatusRepository;
import com.LeaveRequest.LeaveRequest.serviceInterface.IMarriedStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author acer
 */
@Service
public class MarriedStatusDAO implements IMarriedStatus{
    @Autowired
    MarriedStatusRepository marriedstatusRepository;


    @Override
    public Iterable<MarriedStatus> findAllEmp() {
        return marriedstatusRepository.findAll(); //To change body of generated methods, choose Tools | Templates.
    }
}
