package es.projecteEI1027.controller;

import es.projecteEI1027.dao.BeneficiaryDao;
import es.projecteEI1027.dao.CASDao;
import es.projecteEI1027.dao.SocialWorkerDao;
import es.projecteEI1027.dao.VolunteerDao;
import es.projecteEI1027.model.*;
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
    private CAS cas;
    @Autowired
    private BeneficiaryDao userDao;
    @Autowired
    private VolunteerDao volunteerDao;
    @Autowired
    private CASDao casDao;

    @RequestMapping("/login")
    public String login(Model model){
        model.addAttribute("user", new User());

        return "beneficiary/login";
    }
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String checkLogin(@ModelAttribute("user") User usr,
                             BindingResult bindingResult, HttpSession session){
        UserValidator userValidator = new UserValidator();
        userValidator.validate(usr, bindingResult);
        if(bindingResult.hasErrors()){
            return "beneficiary/login";
        }
        if(userDao.getBeneficiaryPerNom(usr.getUser())!=null){
            if(! userDao.getBeneficiaryPerNom(usr.getUser()).getPassword().equals(usr.getPassword())){
                bindingResult.rejectValue("password", "invalid",
                        "La contrasenya es incorrecta");
                return "beneficiary/login";
            }
            Beneficiary user = userDao.getBeneficiaryPerNom(usr.getUser());
            session.setAttribute("user", user);
            return  "beneficiary/indexServices";

        }
        if(volunteerDao.getVolunteerPerUser(usr.getUser()) != null){
            if(! volunteerDao.getVolunteerPerUser(usr.getUser()).getPwd().equals(usr.getPassword())){
                bindingResult.rejectValue("password", "invalid",
                        "La contrasenya es incorrecta");
                return "beneficiary/login";
            }
            Volunteer user = volunteerDao.getVolunteerPerUser(usr.getUser());
            session.setAttribute("user", user);
            if(!user.getAccepted()){
                return "volunteer/noAcceptedYet";
            }
            return "volunteer/indexVolunteer";
        }
        if (casDao.getCASPerUser(usr.getUser()) != null){
            if(! casDao.getCASPerUser(usr.getUser()).getPassword().equals(usr.getPassword())){
                bindingResult.rejectValue("password", "invalid",
                        "La contrasenya es incorrecta");
                return "beneficiary/login";
            }
            CAS user = casDao.getCASPerUser(usr.getUser());
            session.setAttribute("user", user);


        }

        if(userDao.getBeneficiaryPerNom(usr.getUser()) == null && volunteerDao.getVolunteerPerUser(usr.getUser()) == null && casDao.getCASPerUser(usr.getUser()) == null){
            bindingResult.rejectValue("user", "invalid",
                    "No existeix aquest usuari");
            return "beneficiary/login";
        }



        Beneficiary user = userDao.getBeneficiaryPerNom(usr.getUser());
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
        User userDetails = (User) obj;
        if(userDetails.getUser().equals("") )
            errors.rejectValue("user", "obligatori",
                    "L'usuari es incorrecte o no existeix");

        if (userDetails.getPassword().equals(""))
            errors.rejectValue("password", "obligatori",
                    "Contrasenya incorrecta");
    }
}
