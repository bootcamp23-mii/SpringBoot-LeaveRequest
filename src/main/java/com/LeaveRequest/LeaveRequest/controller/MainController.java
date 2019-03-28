/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LeaveRequest.LeaveRequest.controller;

import com.LeaveRequest.LeaveRequest.entities.Employee;
import com.LeaveRequest.LeaveRequest.entities.LeaveType;
import com.LeaveRequest.LeaveRequest.entities.MarriedStatus;
import com.LeaveRequest.LeaveRequest.entities.NationalHoliday;
import com.LeaveRequest.LeaveRequest.entities.Request;
import com.LeaveRequest.LeaveRequest.entities.RequestStatus;
import com.LeaveRequest.LeaveRequest.entities.Status;
import com.LeaveRequest.LeaveRequest.serviceInterface.serviceinterfaceimpl.ApprovalMailService;
import com.LeaveRequest.LeaveRequest.serviceInterface.serviceinterfaceimpl.EmployeeDAO;
import com.LeaveRequest.LeaveRequest.serviceInterface.serviceinterfaceimpl.LeaveTypeDAO;
import com.LeaveRequest.LeaveRequest.serviceInterface.serviceinterfaceimpl.MailService;
import com.LeaveRequest.LeaveRequest.serviceInterface.serviceinterfaceimpl.NationalDAO;
import com.LeaveRequest.LeaveRequest.serviceInterface.serviceinterfaceimpl.MarriedStatusDAO;
import com.LeaveRequest.LeaveRequest.serviceInterface.serviceinterfaceimpl.RequestDAO;
import com.LeaveRequest.LeaveRequest.serviceInterface.serviceinterfaceimpl.RequestStatusDAO;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
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
    @Autowired
    NationalDAO nationalDAO;
    @Autowired
    MarriedStatusDAO msdao;
    @Autowired
    private MailService emailService;
    @Autowired
    private ApprovalMailService approvalMailService;

    @GetMapping("/*")
    public String index(Model model) {
        return "404";
    }

    @GetMapping("/")
    public String indexs(Model model, HttpSession session) {
        if (session.getAttribute("idLogin") == null) {
            return "redirect:/login";
        }
        String id = session.getAttribute("idLogin").toString();
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
    public String checkLogin(@ModelAttribute("loginPost") Employee employee, HttpSession session) {
        String id = employee.getId();
        String password = employee.getPassword();
        Employee eF = edao.findById(id);
        if (BCrypt.checkpw(password, eF.getPassword())) {
            session.setAttribute("idLogin", id);
            session.setAttribute("idRole", eF.getEmployeeRoleList());
            return "redirect:/";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/approval")
    public String approval(Model model, HttpSession session) {
        if (session.getAttribute("idLogin") == null) {
            return "redirect:/login";
        }
        String id = session.getAttribute("idLogin").toString();
//        String id = session.getAttribute("idLogin").toString();
        model.addAttribute("requeststatusData", rsdao.showRequestStatusByIdMan(id));
        model.addAttribute("requeststatussave", new RequestStatus());
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
        model.addAttribute("requeststatusData", rsdao.showRequestStatusAllByIdMan(id));
//        model.addAttribute("requeststatussave", new RequestStatus());
//        model.addAttribute("requeststatusedit", new RequestStatus());
//        model.addAttribute("requeststatusdelete", new RequestStatus());
//        model.addAttribute("requeststatusedit2", new Employee());
        return "historymanager";
    }

    @GetMapping("/image")
    public void image(@RequestParam(value = "id") String id, HttpServletResponse response) throws IOException {
        Employee employee = edao.findById(id);
        byte byteArray[] = employee.getPhoto();
        response.setContentType("image/gif");
        OutputStream os = response.getOutputStream();
        os.write(byteArray);
        os.flush();
        os.close();
    }

    @GetMapping("/historyuser")
    public String historyuser(Model model, HttpSession session) {
        if (session.getAttribute("idLogin") == null) {
            return "redirect:/login";
        }
        String id = session.getAttribute("idLogin").toString();
        model.addAttribute("requestData", rsdao.showRequestStatusAllByIdEmp(id)); 
//        model.addAttribute("requestData", rdao.showRequestAllByIdMan(id));
//        model.addAttribute("requeststatussave", new RequestStatus());
//        model.addAttribute("requeststatusedit", new RequestStatus());
//        model.addAttribute("requeststatusdelete", new RequestStatus());
//        model.addAttribute("requeststatusedit2", new Employee());
        return "historyuser";
    }

    @GetMapping("/addrequest")
    public String addrequest(Model model, HttpSession session) throws Exception {
        if (session.getAttribute("idLogin") == null) {
            return "redirect:/login";
        }
        ArrayList<String> getDateA = new ArrayList<String>();
        Date[] getDate1;
        model.addAttribute("requestData", rdao.findAll());
        model.addAttribute("requestsave", rdao.findAll());
        model.addAttribute("divdata", ltdao.findAll());
        for (NationalHoliday nationalHoliday1 : nationalDAO.findAll()) {
            getDateA.add(nationalHoliday1.getDate().toString());
        }
        model.addAttribute("dinolibur", getDateA);
        return "addrequest";
    }

    @PostMapping("/requestsave")
    public String requestsave(@RequestParam(value = "startdate") String start,
            @RequestParam(value = "enddate") String end,
            @RequestParam(value = "total") String jumlahCuti,
            @RequestParam(value = "type") String type, HttpSession session) throws ParseException {
        Date starts = new SimpleDateFormat("yyyy-MM-dd").parse(start);
        Date ends = new SimpleDateFormat("yyyy-MM-dd").parse(end);
        rdao.saveRequest(new Request("@@", starts, ends, new BigInteger(jumlahCuti), new Employee(session.getAttribute("idLogin").toString()), new LeaveType(type)));
        rsdao.saveRequestStatus(new RequestStatus("@@@", new Date(), "", new Request(rdao.findLastId()), new Status("S1")));
        return "redirect:/addrequest";
    }

    @GetMapping("/adduser")
    public String adduser(Model model, HttpSession session) {
        if (session.getAttribute("idLogin") == null) {
            return "redirect:/login";
        }
        model.addAttribute("employeeData", edao.findAllEmployee());
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
        System.out.println(gendertype);
        edao.savdeEmployee(new Employee("@@", name, new Boolean(gendertype), new BigInteger(quota), email, bcryppass, sdf.parse(joindate), new MarriedStatus(marriedstatus), new Employee(idmanager)));
        Employee esave = edao.findById(edao.findLastId());
        System.out.println(esave.getEmployeeRoleList() + esave.getName());
        emailService.sendMail(esave.getEmail(), "Activation new employee", "Activation new employee", esave.getName(), "Please click this link ", "https://localhost:8083/activation");
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
        System.out.println(gendertype);
        edao.savdeEmployee(new Employee(id, name, Boolean.valueOf(gendertype), new BigInteger(quota), email, pass, sdf.parse(joindate), new MarriedStatus(marriedstatus), new Employee(idmanager)));

        return "redirect:/adduser";
    }
//    @PostMapping("/employeeedit") //@PostMapping{"/regionsave"}
//    public String editemployee(@RequestParam("selectimg") Part img, HttpSession session) throws SQLException{
////        byte byteArray[] = img.getBytes(1,(int)img.length());
//        Employee eedit = edao.findById(session.getAttribute("idLogin").toString());
//        edao.savdeEmployee(new Employee(eedit.getId().toString(),
//                eedit.getName().toString(), 
//                eedit.getGendertype(), 
//                eedit.getQuota(), 
//                eedit.getEmail(), 
//                eedit.getPassword(), 
//                byteArray[], 
//                eedit.getJoindate(), 
//                eedit.getIsactive(), 
//                eedit.getIsdeleted(), 
//                eedit.getMarriedstatus(), 
//                eedit.getIdmanager()));
//        edao.savdeEmployee(new Employee)
//        Employee esave = edao.findById(edao.findLastId());
//        System.out.println(esave.getEmployeeRoleList() + esave.getName());
//        emailService.sendMail(esave.getEmail(), "Activation new employee", "Activation new employee", esave.getName(), "Please click this link ", "localhost:8083/activation");
//        return "redirect:/adduser";
//    }
}
