package es.projecteEI1027.controller;

import es.projecteEI1027.dao.BeneficiaryDao;
import es.projecteEI1027.model.Beneficiary;
import es.projecteEI1027.model.Company;
import es.projecteEI1027.model.Volunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class ServiceController {
    @Autowired
    private BeneficiaryDao userDao;

    @RequestMapping("/services")
    public String login(Model model){
        model.addAttribute("tipo", new String());
        return "beneficiary/services";
    }
    @RequestMapping(value = "/services", method = RequestMethod.POST)
    public String checkServices(@ModelAttribute("tipo") String tipo,
                             BindingResult bindingResult, HttpSession session, Model model){

        if(session.getAttribute("user") == null){
            bindingResult.rejectValue("user", "invalid",
                    "No existeix aquest usuari");
            return "beneficiary/login";
        }
        List<Volunteer> listVolunteer= userDao.getVolunteerPerTipus(tipo);
        List<Company> listCompany= userDao.getCompanyPerTipus(tipo);
        Map<String,Float> precios = userDao.getPrecioContract(listCompany);
        model.addAttribute("volunteers",  listVolunteer);
        model.addAttribute("companies", listCompany);
        model.addAttribute("precios",precios);
        return "beneficiary/listServices";

       /* if(! userDao.getBeneficiaryPerNom(user.getUser()).getPassword().equals(user.getPassword())){
            bindingResult.rejectValue("password", "invalid",
                    "La contrasenya es incorrecta");
            return "beneficiary/login";
        }

        session.setAttribute("user", user);
        if(session.getAttribute("nextUrl") != null){
            return "redirect:/"+(String) session.getAttribute("nextUrl");
        }

        return "redirect:/";*/
    }
}
