package es.projecteEI1027.controller;

import es.projecteEI1027.dao.VolunteerDao;
import es.projecteEI1027.model.Volunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.Calendar;

@Controller
@RequestMapping("/volunteer")
public class VolunteerController {
    private VolunteerDao volunteerDao;
    @Autowired
    public void setVolunteerDao(VolunteerDao volunteerDao){
        this.volunteerDao=volunteerDao;
    }
    @RequestMapping(value="/add")
    public String addVolunteer(Model model) {
        model.addAttribute("volunteer", new Volunteer());
        return "volunteer/add";
    }
    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("volunteer") Volunteer volunteer,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "beneficiary/add";
        volunteer.setAccepted(false);
        volunteer.setApplicationDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        volunteerDao.addVolunteer(volunteer);
        return "redirect:/";
    }
    @RequestMapping("/login")
    public String listServices(HttpSession session, Model model){
        if(session.getAttribute("user") == null){
            model.addAttribute("user", new Volunteer());
            //model.addAttribute("companyServices", contractDao.getContracts());
            return "beneficiary/login";
        }
        //model.addAttribute("companyServices", contractDao.getContracts());
        return "volunteer/indexVolunteer";
    }

}
