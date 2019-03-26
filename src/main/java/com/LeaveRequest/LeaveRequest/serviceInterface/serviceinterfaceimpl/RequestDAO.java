/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LeaveRequest.LeaveRequest.serviceInterface.serviceinterfaceimpl;

import com.LeaveRequest.LeaveRequest.entities.Request;
import com.LeaveRequest.LeaveRequest.repository.RequestRepository;
import com.LeaveRequest.LeaveRequest.serviceInterface.IRequestDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author acer
 */
@Service
public class RequestDAO implements IRequestDAO {

    @Autowired
    RequestRepository requestRepository;

    @Override
    public Iterable<Request> findAll() {
        return requestRepository.findAll();
    }

    @Override
    public Request saveRequest(Request request) {
        return requestRepository.save(request);
    }

    @Override
    public void deleteRequestById(String id) {
        requestRepository.deleteById(id);
    }

    @Override
    public String findLastId() {
        return requestRepository.findLastId();
    }

}
