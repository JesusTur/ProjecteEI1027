package es.projecteEI1027.controller;
import es.projecteEI1027.dao.*;
import es.projecteEI1027.model.CAS;
import es.projecteEI1027.model.Company;
import es.projecteEI1027.model.Volunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.projecteEI1027.model.Beneficiary;

import javax.servlet.http.HttpSession;
import java.io.Serializable;


@Controller
@RequestMapping("/beneficiary")
public class BeneficiaryController {

    private BeneficiaryDao beneficiaryDao;
    private ContractDao contractDao;
    private VolunteerTimeDao volunteerTimeDao;
    private VolunteerDao volunteerDao;
    private CompanyDao companyDao;
    private CASDao casDao;
    @Autowired
    public void setBeneficiaryDao(BeneficiaryDao beneficiaryDao) {
        this.beneficiaryDao=beneficiaryDao;
    }
    public  void setContractDao(ContractDao contractDao){this.contractDao = contractDao;}
    public void setVolunteerTimeDao(VolunteerTimeDao volunteerTimeDao){this.volunteerTimeDao = volunteerTimeDao;}
    @Autowired
    public void setVolunteerDao(VolunteerDao volunteerDao){this.volunteerDao = volunteerDao;}
    @Autowired
    public void setCompanyDao(CompanyDao companyDao){this.companyDao=companyDao;}
    @Autowired
    public void setCasDao(CASDao casDao){this.casDao=casDao;}

    @RequestMapping("/list")
    public String listBeneficiaries(Model model, HttpSession session) {
        if(session.getAttribute("user") == null){
            model.addAttribute("user", new Beneficiary());
            return "beneficiary/login";}
        if(! (session.getAttribute("user") instanceof Beneficiary)){
            return"redirect:/";
        }
        model.addAttribute("beneficiaries", beneficiaryDao.getBeneficiaries());
        return "beneficiary/list";
    }

    @RequestMapping(value="/add")
    public String addBeneficiary(Model model, HttpSession session) {

        model.addAttribute("beneficiary", new Beneficiary());
        return "beneficiary/add";
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("beneficiary") Beneficiary beneficiary,
                                   BindingResult bindingResult, HttpSession session, Model model) {


        if (bindingResult.hasErrors())
            return "beneficiary/add";
        beneficiaryDao.addBeneficiary(beneficiary);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{dni}", method=RequestMethod.GET)
    public String editBeneficiary(Model model, @PathVariable String dni, HttpSession session) {
        if(session.getAttribute("user") == null){
            model.addAttribute("user", new Beneficiary());
            return "beneficiary/login";}
        if(! (session.getAttribute("user") instanceof Beneficiary)){
            return"redirect:/";
        }
        model.addAttribute("beneficiary", beneficiaryDao.getBeneficiary(dni));
        return "beneficiary/update";
    }

    @RequestMapping(value="/update", method=RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("beneficiary") Beneficiary beneficiary,
            BindingResult bindingResult, HttpSession session, Model model) {
        if(session.getAttribute("user") == null){
            model.addAttribute("user", new Beneficiary());
            return "beneficiary/login";}
        if(! (session.getAttribute("user") instanceof Beneficiary)){
            return"redirect:/";
        }
        if (bindingResult.hasErrors())
            return "/beneficiary/update";
        beneficiaryDao.updateBeneficiary(beneficiary);
        return "redirect:list";
    }

    @RequestMapping(value="/delete/{dni}")
    public String processDelete(@PathVariable String dni, HttpSession session, Model model) {
        if(session.getAttribute("user") == null){
            model.addAttribute("user", new Beneficiary());
            return "beneficiary/login";}
        if(! (session.getAttribute("user") instanceof Beneficiary)){
            return"redirect:/";
        }
        beneficiaryDao.deleteBeneficiary(dni);
        return "redirect:../list";
    }

    @RequestMapping("/login")
    public String listServices(HttpSession session, Model model){
        if(session.getAttribute("user") == null){
            model.addAttribute("user", new Beneficiary());
            session.setAttribute("nextUrl", "beneficiaty/services");
            //model.addAttribute("companyServices", contractDao.getContracts());
            //model.addAttribute("volunteerServices", volunteerTimeDao.getVolunteerTimes());
            return "beneficiary/login";
        }
        //model.addAttribute("companyServices", contractDao.getContracts());
        //model.addAttribute("volunteerServices", volunteerTimeDao.getVolunteerTimes());
        if (session.getAttribute("user") instanceof Volunteer){
            Volunteer user = volunteerDao.getVolunteerPerUser(((Volunteer) session.getAttribute("user")).getUser());
            session.setAttribute("user", user);
            if(!user.getAccepted()){
                return "volunteer/noAcceptedYet";
            }
            return "volunteer/indexVolunteer";
        }
        if(session.getAttribute("user") instanceof Company){
            Company user = companyDao.getCompanyPerUser(((Company) session.getAttribute("user")).getUser());
            session.setAttribute("user", user);
            return "company/indexCompany";
        }
        if(session.getAttribute("user") instanceof CAS){
            CAS user = casDao.getCASPerUser(((CAS) session.getAttribute("user")).getUser());
            session.setAttribute("user", user);
            if(user.getUser().equals("casManager")){
                return "cas/company/registerCompany";
            }
            if(user.getUser().equals("casVolunteer")){
                return"cas/volunteer/indexCasVolunteer";
            }
            if(user.getUser().equals("casCommitee")){
                return "cas/committe/indexCommitte";
            }
        }
        return "beneficiary/indexServices";
    }

}
