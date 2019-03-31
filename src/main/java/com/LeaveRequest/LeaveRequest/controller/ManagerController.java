/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LeaveRequest.LeaveRequest.controller;

import com.LeaveRequest.LeaveRequest.entities.Employee;
import com.LeaveRequest.LeaveRequest.entities.MarriedStatus;
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
public class ManagerController {
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
    
    @GetMapping("/approval")
    public String approval(Model model, HttpSession session) {
        if (session.getAttribute("idLogin") == null) {
            return "redirect:/login";
        }
        String id = session.getAttribute("idLogin").toString();
//        String id = session.getAttribute("idLogin").toString();
        model.addAttribute("requeststatusData", rsdao.showRequestStatusByIdMan(id));
        model.addAttribute("requeststatussave", new RequestStatus());
        model.addAttribute("requeststatusAllData", rsdao.showRequestStatusAllByIdMan(id));
//        model.addAttribute("requeststatusedit", new RequestStatus());
//        model.addAttribute("requeststatusdelete", new RequestStatus());
//        model.addAttribute("requeststatusedit2", new Employee());
        return "approval";
    }

    @PostMapping("/requeststatusedit")
    public String edit(@RequestParam(value = "id") String id, @RequestParam(value = "datetime") String datetime, @RequestParam(value = "description") String description,
            @RequestParam(value = "request") String request, @RequestParam(value = "status") String status, @RequestParam(value = "requesttotal", required = false) String requesttotal, @RequestParam(value = "idemp", required = false) String idemp) throws ParseException, MessagingException, IOException, MalformedTemplateNameException, TemplateException {

        rsdao.saveRequestStatus(new RequestStatus(id, sdf.parse(datetime), description, new Request(request), new Status(status)));
        SimpleDateFormat sdfi = new SimpleDateFormat("EEEE, dd-MM-yyyy");

        if (idemp != null) {
            if (rsdao.findById(id).getRequest().getLeavetype().getId().equalsIgnoreCase("JC1")) {
                BigInteger kuota = (edao.findById(idemp)).getQuota();
                BigInteger totals = new BigInteger(requesttotal);
                BigInteger selisih = kuota.subtract(totals);
                edao.savdeEmployee(new Employee((edao.findById(idemp)).getId(), (edao.findById(idemp)).getName(), (edao.findById(idemp)).getGendertype(), selisih, (edao.findById(idemp)).getEmail(),
                        (edao.findById(idemp)).getPassword(), (edao.findById(idemp)).getPhoto(), (edao.findById(idemp)).getJoindate(), (edao.findById(idemp)).getIsactive(), (edao.findById(idemp)).getIsdeleted(),
                        new MarriedStatus((edao.findById(idemp)).getMarriedstatus().getId()), new Employee((edao.findById(idemp)).getIdmanager().getId())));
            }

            approvalMailService.sendEmailService((edao.findById(idemp)).getEmail(), "Approval for " + idemp, "Approve!", (edao.findById(idemp)).getName(), "Congratulation! Your request has been approve!<p>Request Date : <b>" + sdfi.format(rsdao.findById(id).getDatetime())
                    + "</b></p> <p>Leaving for : <br><b>" + sdfi.format(rsdao.findById(id).getRequest().getStartdate()) + "</b> until <b>" + sdfi.format(rsdao.findById(id).getRequest().getEnddate())
                    + "</b></p><br>Total : " + rsdao.findById(id).getRequest().getTotal() + " day(s) <br>Make sure you use your leave as well as possible.");
        } else {
            approvalMailService.sendEmailService((rsdao.findById(id)).getRequest().getEmployee().getEmail(), "Rejection for " + (rsdao.findById(id)).getRequest().getEmployee().getId(), "Rejected!", (rsdao.findById(id)).getRequest().getEmployee().getName(),
                    "Sorry! Your request has been Reject!<p>Request Date : <b>" + sdfi.format(rsdao.findById(id).getDatetime())
                    + "</b></p> <p>Leaving for : <br> <b>" + sdfi.format(rsdao.findById(id).getRequest().getStartdate()) + "</b> until <b>" + sdfi.format(rsdao.findById(id).getRequest().getEnddate())
                    + "</b></p><br>Total : " + rsdao.findById(id).getRequest().getTotal() + " day(s) <br>Reason why was rejected : " + rsdao.findById(id).getDescription() + " <br>Make sure your request is in accordance with applicable leave rules");
            return "redirect:/approval";
        }

        return "redirect:/approval";
    }

    @GetMapping("/historymanager")
    public String historymanager(Model model, HttpSession session) {
        if (session.getAttribute("idLogin") == null) {
            return "redirect:/login";
        }
        String id = session.getAttribute("idLogin").toString();
        model.addAttribute("requeststatusAllData", rsdao.showRequestStatusAllByIdMan(id));
//        model.addAttribute("requeststatussave", new RequestStatus());
//        model.addAttribute("requeststatusedit", new RequestStatus());
//        model.addAttribute("requeststatusdelete", new RequestStatus());
//        model.addAttribute("requeststatusedit2", new Employee());
        return "historymanager";
    }
    
}
