/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LeaveRequest.LeaveRequest.serviceInterface.serviceinterfaceimpl;

import com.LeaveRequest.LeaveRequest.entities.NationalHoliday;
import com.LeaveRequest.LeaveRequest.repository.HolidayRepository;
import com.LeaveRequest.LeaveRequest.serviceInterface.INationalDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Panji Sadewo
 */
@Service
public class NationalDAO implements INationalDAO{

    @Autowired
    HolidayRepository holidayRepository;
    
    @Override
    public Iterable<NationalHoliday> findAll() {
        return holidayRepository.findAll();
    }
    
}
