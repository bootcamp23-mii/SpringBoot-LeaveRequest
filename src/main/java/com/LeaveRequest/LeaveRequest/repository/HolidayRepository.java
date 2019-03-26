/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LeaveRequest.LeaveRequest.repository;

import com.LeaveRequest.LeaveRequest.entities.NationalHoliday;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Panji Sadewo
 */
@Repository
public interface HolidayRepository extends CrudRepository<NationalHoliday, String>{
    
}
