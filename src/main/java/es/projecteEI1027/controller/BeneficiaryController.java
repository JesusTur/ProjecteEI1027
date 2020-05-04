package es.projecteEI1027.controller;
import es.projecteEI1027.dao.ContractDao;
import es.projecteEI1027.dao.VolunteerTimeDao;
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
import es.projecteEI1027.dao.BeneficiaryDao;

import javax.servlet.http.HttpSession;
import java.io.Serializable;


@Controller
@RequestMapping("/beneficiary")
public class BeneficiaryController {

    private BeneficiaryDao beneficiaryDao;
    private ContractDao contractDao;
    private VolunteerTimeDao volunteerTimeDao;

    @Autowired
    public void setBeneficiaryDao(BeneficiaryDao beneficiaryDao) {
        this.beneficiaryDao=beneficiaryDao;
    }
    public  void setContractDao(ContractDao contractDao){this.contractDao = contractDao;}
    public void setVolunteerTimeDao(VolunteerTimeDao volunteerTimeDao){this.volunteerTimeDao = volunteerTimeDao;}

    @RequestMapping("/list")
    public String listBeneficiaries(Model model) {
        model.addAttribute("beneficiaries", beneficiaryDao.getBeneficiaries());
        return "beneficiary/list";
    }

    @RequestMapping(value="/add")
    public String addBeneficiary(Model model) {
        model.addAttribute("beneficiary", new Beneficiary());
        return "beneficiary/add";
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("beneficiary") Beneficiary beneficiary,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "beneficiary/add";
        beneficiaryDao.addBeneficiary(beneficiary);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{dni}", method=RequestMethod.GET)
    public String editBeneficiary(Model model, @PathVariable String dni) {
        model.addAttribute("beneficiary", beneficiaryDao.getBeneficiary(dni));
        return "beneficiary/update";
    }

    @RequestMapping(value="/update", method=RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("beneficiary") Beneficiary beneficiary,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "/beneficiary/update";
        beneficiaryDao.updateBeneficiary(beneficiary);
        return "redirect:list";
    }

    @RequestMapping(value="/delete/{dni}")
    public String processDelete(@PathVariable String dni) {
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
        return "beneficiary/indexServices";
    }

}
