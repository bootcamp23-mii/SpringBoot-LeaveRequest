/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LeaveRequest.LeaveRequest.serviceInterface;

import com.LeaveRequest.LeaveRequest.entities.LeaveType;

/**
 *
 * @author acer
 */
public interface ILeaveTypeDAO {
    Iterable<LeaveType> findAll();
    LeaveType saveRequest(LeaveType leavetype);
    void deleteRequestById(String id);
    Iterable<LeaveType> showExcBurn();
}
