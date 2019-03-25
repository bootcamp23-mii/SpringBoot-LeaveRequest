/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LeaveRequest.LeaveRequest.serviceInterface.serviceinterfaceimpl;

import com.LeaveRequest.LeaveRequest.entities.RequestStatus;
import com.LeaveRequest.LeaveRequest.repository.RequestStatusRepository;
import com.LeaveRequest.LeaveRequest.serviceInterface.IRequestStatusDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author AdhityaWP
 */
@Service
public class RequestStatusDAO implements IRequestStatusDAO{
    @Autowired
    RequestStatusRepository requestStatusRepository;

    @Override
    public Iterable<RequestStatus> findAll() {
        return requestStatusRepository.findAll();
    }

    @Override
    public RequestStatus saveRequestStatus(RequestStatus requestStatus) {
        return requestStatusRepository.save(requestStatus);
    }

    @Override
    public void deleteRequestStatusById(String id) {
        requestStatusRepository.deleteById(id);
    }

    @Override
    public Iterable<RequestStatus> showRequestStatusByIdMan(String id) {
        return requestStatusRepository.showByIdMan(id);
    }
    
}
