package es.projecteEI1027.controller;

import es.projecteEI1027.dao.VolunteerDao;
import es.projecteEI1027.dao.VolunteerTimeDao;
import es.projecteEI1027.model.*;
import javafx.util.converter.LocalDateTimeStringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/volunteer")
public class VolunteerController {
    private VolunteerTimeDao volunteerTimeDao;
    private VolunteerDao volunteerDao;
    @Autowired
    public void setVolunteerDao(VolunteerDao volunteerDao){
        this.volunteerDao=volunteerDao;
    }
    @Autowired
    public void setVolunteerTimeDao(VolunteerTimeDao volunteerTimeDao) {
        this.volunteerTimeDao = volunteerTimeDao;
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

    @RequestMapping("/list")
    public String listBeneficiaries(Model model, HttpSession session){
        Volunteer user = (Volunteer) session.getAttribute("user");
        Volunteer volunteer = volunteerDao.getVolunteerPerUser(user.getUser());
        List<Beneficiary> assignedBeneficiaries = volunteerTimeDao.getRelatedBeneficiaries(volunteer.getDni());
        Map<LocalDateTime,LocalDateTime> mapa = volunteerTimeDao.getVolunteerTime(volunteer.getDni());
        model.addAttribute("mapa", mapa);
        model.addAttribute("assignedBeneficiaries", assignedBeneficiaries);
        return "volunteer/listBeneficiariesVol";
    }
    @RequestMapping("/hourAdd")
    public String addHour(Model model){
        VolunteerTime volunteerTime = new VolunteerTime();
        Calendar fechaHoy = Calendar.getInstance();
        model.addAttribute("fechaHoy", fechaHoy);
        model.addAttribute("volunteerTime", volunteerTime);
        return "volunteer/hourAdd";
    }

    @RequestMapping(value="/hourAdd", method=RequestMethod.POST)
    public String processAddVolunteerTime(@ModelAttribute("volunteerTime") VolunteerTime volunteerTime, HttpSession session) {
        volunteerTime.setDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        Volunteer user = (Volunteer) session.getAttribute("user");
        Volunteer volunteer = volunteerDao.getVolunteerPerUser(user.getUser());
        volunteerTime.setDniVolunteer(volunteer.getDni());
        volunteerTime.setDniBeneficiary(null);
        volunteerTime.setAvailable(true);
        volunteerTimeDao.addVolunteerTime(volunteerTime);
        return "/volunteer/listBeneficiariesVol";
    }

    @RequestMapping("/modifiedHour")
    public String modifyHour(Model model, HttpSession session){
        Volunteer user = (Volunteer) session.getAttribute("user");
        Volunteer volunteer = volunteerDao.getVolunteerPerUser(user.getUser());
        List<VolunteerTime> lista = volunteerTimeDao.getFreeTimeList(volunteer.getDni());
        if(lista.isEmpty()){
            return "volunteer/listaVacia";
        }
        model.addAttribute("lista", lista);
        return "volunteer/modifyHour";
    }
    @RequestMapping(value="/modifyTimeNow/{beginningTime}", method=RequestMethod.GET)
    public String processmodifyVolunteerTime(@PathVariable String beginningTime, HttpSession session, Model model) {
        Volunteer user = (Volunteer) session.getAttribute("user");
        Volunteer volunteer = volunteerDao.getVolunteerPerUser(user.getUser());
        session.setAttribute("time",beginningTime);
        VolunteerTime volunteerTime = volunteerTimeDao.getVolunteerTime(volunteer.getDni(),LocalDateTime.parse(beginningTime));
        model.addAttribute("volunteerTime",volunteerTime);
        return "/volunteer/modifyTimeNow";
    }
    @RequestMapping(value="/removeTimeNow/{beginningTime}", method=RequestMethod.GET)
    public String processremoveVolunteerTime(@PathVariable String beginningTime, HttpSession session, Model model) {
        Volunteer user = (Volunteer) session.getAttribute("user");
        Volunteer volunteer = volunteerDao.getVolunteerPerUser(user.getUser());
        session.setAttribute("time",beginningTime);
        volunteerTimeDao.deleteVolunteerTime(volunteer.getDni(),LocalDateTime.parse(beginningTime));
        return "/volunteer/listBeneficiariesVol";
    }
    @RequestMapping(value="/update", method=RequestMethod.POST)
    public String processUpdate(@ModelAttribute("volunteerTime") VolunteerTime volunteerTime, HttpSession session) {
        volunteerTime.setDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        Volunteer user = (Volunteer) session.getAttribute("user");
        Volunteer volunteer = volunteerDao.getVolunteerPerUser(user.getUser());
        volunteerTime.setDniVolunteer(volunteer.getDni());
        volunteerTime.setDniBeneficiary(null);
        volunteerTimeDao.updateTime(volunteer.getDni(),LocalDateTime.parse(session.getAttribute("time").toString()),volunteerTime.getBeginningTime(),volunteerTime.getEndingTime());
        return "/volunteer/listBeneficiariesVol";
    }


}