package es.projecteEI1027.controller;
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


@Controller
@RequestMapping("/beneficiary")
public class BeneficiaryController {

    private BeneficiaryDao beneficiaryDao;

    @Autowired
    public void setBeneficiaryDao(BeneficiaryDao beneficiaryDao) {
        this.beneficiaryDao=beneficiaryDao;
    }

    @RequestMapping("/list")
    public String listBeneficiaries(Model model) {
        model.addAttribute("beneficiaries", beneficiaryDao.getBeneficiaries());
        return "beneficiary/list";
    }

    @RequestMapping(value="/add")
    public String addBeneficary(Model model) {
        model.addAttribute("beneficiary", new Beneficiary());
        return "beneficiary/add";
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("beneficary") Beneficiary beneficiary,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "beneficary/add";
        beneficiaryDao.addBeneficiary(beneficiary);
        return "redirect:list.html";
    }

    @RequestMapping(value="/update/{dni}", method = RequestMethod.GET)
    public String editNadador(Model model, @PathVariable String nom) {
        model.addAttribute("beneficary", beneficiaryDao.getBeneficiary(nom));
        return "beneficary/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("beneficary") Beneficiary beneficiary,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "templates/beneficiary/update";
        beneficiaryDao.updateBeneficiary(beneficiary);
        return "redirect:list";
    }

    @RequestMapping(value="/delete/{dni}")
    public String processDelete(@PathVariable String nom) {
        beneficiaryDao.deleteBeneficiary(nom);
        return "redirect:../list";
    }

}
