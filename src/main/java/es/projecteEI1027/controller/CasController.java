package es.projecteEI1027.controller;

import es.projecteEI1027.dao.*;
import es.projecteEI1027.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/cas")
public class CasController {
    private CASDao casDao;
    private BeneficiaryDao beneficiaryDao;
    private CompanyDao companyDao;
    private ContractDao contractDao;
    private VolunteerDao volunteerDao;

    @Autowired
    public void setBeneficiaryDao(BeneficiaryDao beneficiaryDao) {
        this.beneficiaryDao=beneficiaryDao;
    }
    @Autowired
    public void setCasDao(CASDao casDao){this.casDao=casDao;}
    @Autowired
    public  void setCompanyDao(CompanyDao companyDao){this.companyDao=companyDao;}
    @Autowired
    public  void setContractDao(ContractDao contractDao){this.contractDao=contractDao;}
    @Autowired
    public void setVolunteerDao(VolunteerDao volunteerDao){this.volunteerDao=volunteerDao;}

    @RequestMapping(value = "/addCompany")
    public String addCompany(Model model, HttpSession session){
        if(session.getAttribute("user") == null){
            model.addAttribute("user", new CAS());
            return "beneficiary/login";}
        if(! (session.getAttribute("user") instanceof CAS)){
            return"redirect:/";
        }
        CAS user = (CAS) session.getAttribute("user");
        if(!(user.getUser().equals("casManager"))) {
            return"redirect:/";
        }


        model.addAttribute("company", new Company());
        ServiceType[] types = ServiceType.values();
        model.addAttribute("types", types);
        return "cas/company/addCompany";
    }
    @RequestMapping(value="/addCompany", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("company") Company company, Model model, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            ServiceType[] types = ServiceType.values();
            model.addAttribute("types", types);
            return "cas/company/addCompany";
        }
        company.setRegisteredDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        System.out.println(company.getRegisteredDate());
        companyDao.addCompany(company);
        return "cas/company/registerCompany.html";

    }
    @RequestMapping(value = "/acceptVolunteer")
    public String acceptVolunteer(Model model){
        List<Volunteer> volunteers = volunteerDao.getUnacceptedVolunteers();
        model.addAttribute("volunteers", volunteers);
        return"cas/volunteer/acceptVolunteer";
    }
    @RequestMapping(value = "/acceptVolunteer/{dni}", method=RequestMethod.GET)
    public String processAcceptVolunteerSubmit(@PathVariable String dni, Model model) {
        Volunteer volunteer = volunteerDao.getVolunteer(dni);
        volunteer.setAccepted(true);
        volunteer.setAcceptationDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        volunteerDao.acceptVolunteer(volunteer);
        System.out.println(volunteerDao.getVolunteer(dni).getAccepted());
        return"cas/volunteer/indexCasVolunteer";
    }
}
