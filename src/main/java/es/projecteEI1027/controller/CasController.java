package es.projecteEI1027.controller;

import es.projecteEI1027.dao.*;
import es.projecteEI1027.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public void setCasDao(CASDao casDao){this.casDao=casDao;}
    @Autowired
    public  void setCompanyDao(CompanyDao companyDao){this.companyDao=companyDao;}
    public  void setContractDao(ContractDao contractDao){this.contractDao=contractDao;}
    public void setVolunteerDao(VolunteerDao volunteerDao){this.volunteerDao=volunteerDao;}

    @RequestMapping(value = "/addCompany")
    public String addCompany(Model model){
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
}
