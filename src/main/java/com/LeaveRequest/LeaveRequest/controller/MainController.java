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
import com.LeaveRequest.LeaveRequest.serviceInterface.serviceinterfaceimpl.EmployeeDAO;
import com.LeaveRequest.LeaveRequest.serviceInterface.serviceinterfaceimpl.LeaveTypeDAO;
import com.LeaveRequest.LeaveRequest.serviceInterface.serviceinterfaceimpl.RequestDAO;
import com.LeaveRequest.LeaveRequest.serviceInterface.serviceinterfaceimpl.RequestStatusDAO;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tools.BCrypt;

/**
 *
 * @author AdhityaWP
 */
@Controller
public class MainController {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    RequestDAO rdao;
    @Autowired
    RequestStatusDAO rsdao;
    @Autowired
    EmployeeDAO edao;
    @Autowired
    LeaveTypeDAO ltdao;

    @GetMapping("/")
    public String index(Model model) {
        String id = "11202";
        model.addAttribute("dataEmployee", edao.findById(id));
        Integer kuota = ((edao.findById(id)).getQuota()).intValue();
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        Integer month = cal.get(Calendar.MONTH) + 1;

        if (kuota <= month) {            
            model.addAttribute("monthnow", month);
            Integer lastyear = kuota - month;
            model.addAttribute("lastyear", lastyear);
        } else {
            model.addAttribute("monthnow", month);
            Integer lastyear = kuota - month;
            model.addAttribute("lastyear", lastyear);
        }
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginPost", new Employee());
        return "login";
    }

    @RequestMapping(value = "/loginPost", method = RequestMethod.POST)  //@PostMapping("/regionsave")
    public String checkLogin(@ModelAttribute("loginPost") Employee employee) {
        String id = employee.getId();
        String password = employee.getPassword();
        Employee eF = edao.findById(id);
        if (BCrypt.checkpw(password, eF.getPassword())) {
            return "redirect:/addrequest";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/approval")
    public String approval(Model model) {
        String id = "11201";
        model.addAttribute("requeststatusData", rsdao.showRequestStatusByIdMan(id));
        model.addAttribute("requeststatussave", new RequestStatus());
//        model.addAttribute("requeststatusedit", new RequestStatus());
//        model.addAttribute("requeststatusdelete", new RequestStatus());
//        model.addAttribute("requeststatusedit2", new Employee());
        return "approval";
    }

    @PostMapping("/requeststatusedit")
    public String edit(@RequestParam(value = "id") String id, @RequestParam(value = "datetime") String datetime, @RequestParam(value = "description") String description,
            @RequestParam(value = "request") String request, @RequestParam(value = "status") String status, @RequestParam(value = "requesttotal", required = false) String requesttotal, @RequestParam(value = "idemp", required = false) String idemp) throws ParseException {
        rsdao.saveRequestStatus(new RequestStatus(id, sdf.parse(datetime), description, new Request(request), new Status(status)));

        if (idemp != null) {
            BigInteger kuota = (edao.findById(idemp)).getQuota();
            BigInteger totals = new BigInteger(requesttotal);
            BigInteger selisih = kuota.subtract(totals);
            edao.savdeEmployee(new Employee((edao.findById(idemp)).getId(), (edao.findById(idemp)).getName(), (edao.findById(idemp)).getGendertype(), selisih, (edao.findById(idemp)).getEmail(),
                    (edao.findById(idemp)).getPassword(), (edao.findById(idemp)).getPhoto(), (edao.findById(idemp)).getJoindate(), (edao.findById(idemp)).getIsactive(), (edao.findById(idemp)).getIsdeleted(),
                    new MarriedStatus((edao.findById(idemp)).getMarriedstatus().getId()), new Employee((edao.findById(idemp)).getIdmanager().getId())));
        } else {
            return "redirect:/approval";
        }

        return "redirect:/approval";
    }

    @GetMapping("/historymanager")
    public String historymanager(Model model) {
        String id = "11201";
        model.addAttribute("requeststatusData", rsdao.showRequestStatusAllByIdMan(id));
//        model.addAttribute("requeststatussave", new RequestStatus());
//        model.addAttribute("requeststatusedit", new RequestStatus());
//        model.addAttribute("requeststatusdelete", new RequestStatus());
//        model.addAttribute("requeststatusedit2", new Employee());
        return "historymanager";
    }

    @GetMapping("/historyuser")
    public String historyuser(Model model) {
        String id = "11201";
        model.addAttribute("requestData", rsdao.showRequestStatusByIdMan(id));
//        model.addAttribute("requestData", rdao.showRequestAllByIdMan(id));
//        model.addAttribute("requeststatussave", new RequestStatus());
//        model.addAttribute("requeststatusedit", new RequestStatus());
//        model.addAttribute("requeststatusdelete", new RequestStatus());
//        model.addAttribute("requeststatusedit2", new Employee());
        return "historyuser";
    }

    @GetMapping("/addrequest")
    public String addrequest(Model model) {
        model.addAttribute("requestData", rdao.findAll());
        model.addAttribute("requestsave", new Request());
        model.addAttribute("divdata", ltdao.findAll());

        return "addrequest";
    }

    @RequestMapping(value = "/requestsave", method = RequestMethod.POST) //@PostMapping{"/regionsave"}
    public String save(@ModelAttribute("requestsave") Request request) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
//        System.out.println(request.getId());
//        System.out.println(dateFormat.format(request.getStartdate()));
//        System.out.println(request.getEnddate());
        rdao.saveRequest(request);
        return "redirect:/addrequest";
    }
}
