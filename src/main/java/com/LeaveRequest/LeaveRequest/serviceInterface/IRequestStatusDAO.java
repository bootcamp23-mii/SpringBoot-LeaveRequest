/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LeaveRequest.LeaveRequest.serviceInterface;

import com.LeaveRequest.LeaveRequest.entities.RequestStatus;
import java.util.Optional;

/**
 *
 * @author AdhityaWP
 */
public interface IRequestStatusDAO {
    Iterable<RequestStatus> findAll();
    RequestStatus saveRequestStatus(RequestStatus requestStatus);
    void deleteRequestStatusById(String id);
    Iterable<RequestStatus> showRequestStatusByIdMan(String id);
    Iterable<RequestStatus> showRequestStatusAllByIdMan(String id);
    Iterable<RequestStatus> showRequestStatusAllByIdEmp(String id);
    RequestStatus findById(String id);
}
