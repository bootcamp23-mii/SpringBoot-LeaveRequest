/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LeaveRequest.LeaveRequest.controller;

import com.LeaveRequest.LeaveRequest.entities.Request;
import com.LeaveRequest.LeaveRequest.entities.RequestStatus;
import com.LeaveRequest.LeaveRequest.entities.Status;
import com.LeaveRequest.LeaveRequest.serviceInterface.serviceinterfaceimpl.RequestStatusDAO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author AdhityaWP
 */
@Controller
public class MainController {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    @Autowired
    RequestStatusDAO rsdao;
    
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("requeststatusData", rsdao.findAll());
        model.addAttribute("requeststatussave", new RequestStatus());
        model.addAttribute("requeststatusedit", new RequestStatus());
        model.addAttribute("requeststatusdelete", new RequestStatus());
        return "index";
    }
    
    @GetMapping("/approval")
    public String approval(Model model) {
        model.addAttribute("requeststatusData", rsdao.findAll());
        model.addAttribute("requeststatussave", new RequestStatus());
        model.addAttribute("requeststatusedit", new RequestStatus());
        model.addAttribute("requeststatusdelete", new RequestStatus());
        return "approval";
    }
    
    @RequestMapping(value = "/requeststatusedit", method = RequestMethod.POST)  
    public String edit(@RequestParam(value = "id") String id, @RequestParam(value = "datetime") String datetime, @RequestParam(value = "description") String description,
            @RequestParam(value = "request") String request, @RequestParam(value = "status") String status) throws ParseException {
        rsdao.saveRequestStatus(new RequestStatus(id, sdf.parse(datetime), description, new Request(request), new Status(status)));
        return "redirect:/approval";
    }
}
