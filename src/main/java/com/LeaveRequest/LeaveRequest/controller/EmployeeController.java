/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LeaveRequest.LeaveRequest.controller;

import com.LeaveRequest.LeaveRequest.entities.Employee;
import com.LeaveRequest.LeaveRequest.entities.LeaveType;
import com.LeaveRequest.LeaveRequest.entities.NationalHoliday;
import com.LeaveRequest.LeaveRequest.entities.Request;
import com.LeaveRequest.LeaveRequest.entities.RequestStatus;
import com.LeaveRequest.LeaveRequest.entities.Status;
import com.LeaveRequest.LeaveRequest.serviceInterface.serviceinterfaceimpl.ApprovalMailService;
import com.LeaveRequest.LeaveRequest.serviceInterface.serviceinterfaceimpl.EmployeeDAO;
import com.LeaveRequest.LeaveRequest.serviceInterface.serviceinterfaceimpl.LeaveTypeDAO;
import com.LeaveRequest.LeaveRequest.serviceInterface.serviceinterfaceimpl.MailService;
import com.LeaveRequest.LeaveRequest.serviceInterface.serviceinterfaceimpl.MarriedStatusDAO;
import com.LeaveRequest.LeaveRequest.serviceInterface.serviceinterfaceimpl.NationalDAO;
import com.LeaveRequest.LeaveRequest.serviceInterface.serviceinterfaceimpl.RequestDAO;
import com.LeaveRequest.LeaveRequest.serviceInterface.serviceinterfaceimpl.RequestStatusDAO;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author AdhityaWP
 */
@Controller
public class EmployeeController {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    RequestDAO rdao;
    @Autowired
    RequestStatusDAO rsdao;
    @Autowired
    EmployeeDAO edao;
    @Autowired
    LeaveTypeDAO ltdao;
    @Autowired
    NationalDAO nationalDAO;
    @Autowired
    MarriedStatusDAO msdao;
    @Autowired
    private MailService emailService;
    @Autowired
    private ApprovalMailService approvalMailService;
    
    
    @GetMapping("/addrequest")
    public String addrequest(Model model, HttpSession session) throws Exception {
        if (session.getAttribute("idLogin") == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("requestcountApproval", rsdao.countApproval(session.getAttribute("idLogin").toString()));
        ArrayList<String> getDateA = new ArrayList<String>();
        Date[] getDate1;
        model.addAttribute("requestData", rdao.findAll());
        model.addAttribute("requestsave", rdao.findAll());
        model.addAttribute("divdata", ltdao.findAll());
        for (NationalHoliday nationalHoliday1 : nationalDAO.findAll()) {
            getDateA.add(nationalHoliday1.getDate().toString());
        }
        Employee eaddreq = edao.findById(session.getAttribute("idLogin").toString());
        model.addAttribute("dinolibur", getDateA);
        model.addAttribute("totalLeave", eaddreq.getQuota());
        return "addrequest";
    }

    @PostMapping("/requestsave")
    public String requestsave(@RequestParam(value = "startdate") String start,
            @RequestParam(value = "enddate") String end,
            @RequestParam(value = "total") String jumlahCuti,
            @RequestParam(value = "type") String type, HttpSession session) throws ParseException, MessagingException, IOException, MalformedTemplateNameException, TemplateException {
        Date starts = new SimpleDateFormat("yyyy-MM-dd").parse(start);
        Date ends = new SimpleDateFormat("yyyy-MM-dd").parse(end);
        rdao.saveRequest(new Request("@@", starts, ends, new BigInteger(jumlahCuti), new Employee(session.getAttribute("idLogin").toString()), new LeaveType(type)));
        rsdao.saveRequestStatus(new RequestStatus("@@@", new Date(), "", new Request(rdao.findLastId()), new Status("S1")));
        String id = session.getAttribute("idLogin").toString();
        String idmanager = (edao.findById(id)).getIdmanager().getId();
        approvalMailService.sendEmailService( edao.findById(idmanager).getEmail(), "Request Leaving from " + id, "You've got a new request list", (edao.findById(id)).getName(), "Check Application for detail.<br>"+"http://localhost:8085/approval");
        return "redirect:/addrequest";
    }
    
    @GetMapping("/historyuser")
    public String historyuser(Model model, HttpSession session) {
        if (session.getAttribute("idLogin") == null) {
            return "redirect:/login";
        }
        String id = session.getAttribute("idLogin").toString();
        model.addAttribute("requestData", rsdao.showRequestStatusAllByIdEmp(id)); 
        model.addAttribute("requestcountApproval", rsdao.countApproval(session.getAttribute("idLogin").toString()));
//        model.addAttribute("requestData", rdao.showRequestAllByIdMan(id));
//        model.addAttribute("requeststatussave", new RequestStatus());
//        model.addAttribute("requeststatusedit", new RequestStatus());
//        model.addAttribute("requeststatusdelete", new RequestStatus());
//        model.addAttribute("requeststatusedit2", new Employee());
        return "historyuser";
    }
}
