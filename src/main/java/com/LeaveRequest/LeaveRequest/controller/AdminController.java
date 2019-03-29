/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LeaveRequest.LeaveRequest.controller;

import com.LeaveRequest.LeaveRequest.entities.Employee;
import com.LeaveRequest.LeaveRequest.entities.EmployeeRole;
import com.LeaveRequest.LeaveRequest.entities.MarriedStatus;
import com.LeaveRequest.LeaveRequest.serviceInterface.serviceinterfaceimpl.ApprovalMailService;
import com.LeaveRequest.LeaveRequest.serviceInterface.serviceinterfaceimpl.EmployeeDAO;
import com.LeaveRequest.LeaveRequest.serviceInterface.serviceinterfaceimpl.EmployeeRoleDAO;
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
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
public class AdminController {

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
    EmployeeRoleDAO employeeRoleDAO;
    @Autowired
    private MailService emailService;
    @Autowired
    private ApprovalMailService approvalMailService;

    @GetMapping("/adduser")
    public String adduser(Model model, HttpSession session) {
        if (session.getAttribute("idLogin") == null) {
            return "redirect:/login";
        }
//        ArrayList<String> roleBro = new ArrayList<String>();
//        for (EmployeeRole employeeRole : employeeRoleDAO.findEmployeeById(session.getId())) {
//            roleBro.add(employeeRole.getRole().getId());
//            System.out.println((employeeRole.getRole().getId()));
//            System.out.println("asdsad");
//        }
//        model.addAttribute("idRole", roleBro);
        model.addAttribute("employeeData", edao.findAllEmployee());
        model.addAttribute("employeeData1", edao.findEmployeeById());
        model.addAttribute("employeesave", new Employee());
        model.addAttribute("employeeedit", new Employee());
        model.addAttribute("adddata", msdao.findAllEmp());
        model.addAttribute("addgender", edao.findAllEmployee());
        return "adduser";
    }

    @PostMapping("/employeesave") //@PostMapping{"/regionsave"}
    public String saveemployee(String id, String name, @RequestParam("gendertype") String gendertype, @RequestParam("quota") String quota,
            String email, @RequestParam("joindate") String joindate, @RequestParam("marriedstatus") String marriedstatus, @RequestParam("idmanager") String idmanager) throws ParseException, MessagingException, IOException, MalformedTemplateNameException, TemplateException {
        String password = Double.toString(Math.random());
        String bcryppass = BCrypt.hashpw(password, BCrypt.gensalt());

        edao.savdeEmployee(new Employee("@@", name, new Boolean(gendertype), new BigInteger("0"), email, bcryppass, sdf.parse(joindate), new MarriedStatus(marriedstatus), new Employee((idmanager))));
//        edao.savdeEmployee(new Employee("@@", "asd", true, new BigInteger("1"), "12@gmail.com", "1223", sdf.parse("2018-03-03"), new MarriedStatus("SN1"), new Employee("11201")));
        Employee esave = edao.findById(edao.findLastId());
        String passwordUrl = URLEncoder.encode(esave.getPassword());
//        System.out.println(esave.getEmployeeRoleList() + esave.getName());
        emailService.sendMail(esave.getEmail(), "Activation new employee", "Activation new employee", esave.getName(), "Please click this link ", "http://localhost:8085/activation?id=" + esave.getId() + "&token=" + passwordUrl + "");
        return "redirect:/adduser";
    }

    @RequestMapping(value = "/employeedelete", method = RequestMethod.GET) //@RequestParam{value "regionid"} 
    public String delete(@RequestParam(value = "employeeid") String idemployee) {
        edao.deleteEmployeeById(idemployee);
        return "redirect:/adduser";
    }

    @PostMapping("/employeeedit")
    public String edit(@RequestParam("id") String id, String name, @RequestParam("gendertype") String gendertype, @RequestParam("quota") String quota,
            String email, @RequestParam("joindate") String joindate, @RequestParam("marriedstatus") String marriedstatus, @RequestParam("idmanager") String idmanager) throws ParseException {
        String pass = (edao.findById(id)).getPassword();
        System.out.println(idmanager);
        edao.savdeEmployee(new Employee(id, name, Boolean.valueOf(gendertype), new BigInteger(quota), email, pass, sdf.parse(joindate), new MarriedStatus(marriedstatus), new Employee((idmanager))));

        return "redirect:/adduser";
    }

}
