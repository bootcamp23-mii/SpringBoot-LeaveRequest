/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LeaveRequest.LeaveRequest.serviceInterface.serviceinterfaceimpl;

import com.LeaveRequest.LeaveRequest.entities.LeaveType;
import com.LeaveRequest.LeaveRequest.repository.LeaveTypeRepository;
import com.LeaveRequest.LeaveRequest.serviceInterface.ILeaveTypeDAO;
import static java.util.Collections.list;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author acer
 */
@Service
public class LeaveTypeDAO implements ILeaveTypeDAO{
    @Autowired
    LeaveTypeRepository leavetypeRepository;

    @Override
    public Iterable<LeaveType> findAll() {
        return leavetypeRepository.findAll();
    }

    @Override
    public void deleteRequestById(String id) {
        leavetypeRepository.deleteById(id);
    }

    @Override
    public LeaveType saveRequest(LeaveType leavetype) {
        return leavetypeRepository.save(leavetype); //To change body of generated methods, choose Tools | Templates.
    }
    

  
 
    
}
