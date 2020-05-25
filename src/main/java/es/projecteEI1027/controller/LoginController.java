package es.projecteEI1027.controller;

import es.projecteEI1027.dao.BeneficiaryDao;
import es.projecteEI1027.dao.CASDao;
import es.projecteEI1027.dao.SocialWorkerDao;
import es.projecteEI1027.dao.VolunteerDao;
import es.projecteEI1027.model.Beneficiary;
import es.projecteEI1027.model.CAS;
import es.projecteEI1027.model.SocialWorker;
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

@Controller
public class LoginController {
    private Beneficiary beneficiary;
    private Volunteer volunteer;
    private SocialWorker socialWorker;
    private CAS cas;
    @Autowired
    private BeneficiaryDao userDao;
    private VolunteerDao volunteerDao;
    private SocialWorkerDao socialWorkerDao;
    private CASDao casDao;

    @RequestMapping("/login")
    public String login(Model model){
        model.addAttribute("user", new Beneficiary());

        return "beneficiary/login";
    }
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String checkLogin(@ModelAttribute("user") Beneficiary user,
                             BindingResult bindingResult, HttpSession session){
        UserValidator userValidator = new UserValidator();
        userValidator.validate(user, bindingResult);
        if(bindingResult.hasErrors()){
            return "beneficiary/login";
        }
        if(userDao.getBeneficiaryPerNom(user.getUser()) == null){
            bindingResult.rejectValue("user", "invalid",
                    "No existeix aquest usuari");
            return "beneficiary/login";
        }

        if(! userDao.getBeneficiaryPerNom(user.getUser()).getPassword().equals(user.getPassword())){
            bindingResult.rejectValue("password", "invalid",
                    "La contrasenya es incorrecta");
            return "beneficiary/login";
        }

        session.setAttribute("user", user);
        return  "beneficiary/indexServices";
        /*if(session.getAttribute("nextUrl") != null){
            return "redirect:/"+(String) session.getAttribute("nextUrl");
        }

        return "redirect:/";*/
    }
}
class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Beneficiary.class.isAssignableFrom(cls);
    }
    @Override
    public void validate(Object obj, Errors errors) {
        Beneficiary userDetails = (Beneficiary) obj;
        BeneficiaryDao userDao = new BeneficiaryDao();
        if(userDetails.getUser().equals("") )
            errors.rejectValue("user", "obligatori",
                    "L'usuari es incorrecte o no existeix");

        if (userDetails.getPassword().equals(""))
            errors.rejectValue("password", "obligatori",
                    "Contrasenya incorrecta");
    }
}
