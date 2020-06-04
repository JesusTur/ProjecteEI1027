package es.projecteEI1027.controller;

import es.projecteEI1027.dao.BeneficiaryDao;
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
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/volunteer")
public class VolunteerController {
    @Autowired
    private BeneficiaryDao userDao;
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
    public String addVolunteer(Model model, HttpSession session) {
        if(session.getAttribute("user") == null){
            model.addAttribute("user", new Volunteer());
            return "beneficiary/login";}
        if(! (session.getAttribute("user") instanceof Volunteer)){
            return"redirect:/";
        }
        model.addAttribute("volunteer", new Volunteer());
        return "volunteer/add";
    }
    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("volunteer") Volunteer volunteer,
                                   BindingResult bindingResult, HttpSession session, Model model) {
        if(session.getAttribute("user") == null){
            model.addAttribute("user", new Volunteer());
            return "beneficiary/login";}
        if(! (session.getAttribute("user") instanceof Volunteer)){
            return"redirect:/";
        }
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
            return "beneficiary/login";}
        if(! (session.getAttribute("user") instanceof Volunteer)){
            return"redirect:/";
        }
        return "volunteer/indexVolunteer";
    }

    @RequestMapping("/list")
    public String listBeneficiaries(Model model, HttpSession session){
        if(session.getAttribute("user") == null){
            model.addAttribute("user", new Volunteer());
            return "beneficiary/login";}
        if(! (session.getAttribute("user") instanceof Volunteer)){
            return"redirect:/";
        }
        Volunteer user = (Volunteer) session.getAttribute("user");
        Volunteer volunteer = volunteerDao.getVolunteerPerUser(user.getUser());
        List<Beneficiary> assignedBeneficiaries = volunteerTimeDao.getRelatedBeneficiaries(volunteer.getDni());
        Map<LocalDateTime,LocalDateTime> mapa = volunteerTimeDao.getVolunteerTime(volunteer.getDni());
        model.addAttribute("mapa", mapa);
        model.addAttribute("assignedBeneficiaries", assignedBeneficiaries);
        return "volunteer/listBeneficiariesVol";
    }
    @RequestMapping("/hourAdd")
    public String addHour(Model model, HttpSession session){
        if(session.getAttribute("user") == null){
            model.addAttribute("user", new Volunteer());
            return "beneficiary/login";}
        if(! (session.getAttribute("user") instanceof Volunteer)){
            return"redirect:/";
        }
        VolunteerTime volunteerTime = new VolunteerTime();
        LocalDateTime fechaHoy = LocalDateTime.now();
        String fecha = fechaHoy.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        model.addAttribute("fechaHoy", fecha);
        model.addAttribute("volunteerTime", volunteerTime);
        return "volunteer/hourAdd";
    }

    @RequestMapping(value="/hourAdd", method=RequestMethod.POST)
    public String processAddVolunteerTime(@ModelAttribute("volunteerTime") VolunteerTime volunteerTime, HttpSession session, Model model) {
        if(session.getAttribute("user") == null){
            model.addAttribute("user", new Volunteer());
            return "beneficiary/login";}
        if(! (session.getAttribute("user") instanceof Volunteer)){
            return"redirect:/";
        }
        volunteerTime.setDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        Volunteer user = (Volunteer) session.getAttribute("user");
        Volunteer volunteer = volunteerDao.getVolunteerPerUser(user.getUser());
        volunteerTime.setDniVolunteer(volunteer.getDni());
        volunteerTime.setDniBeneficiary(null);
        volunteerTime.setAvailable(true);
        volunteerTimeDao.addVolunteerTime(volunteerTime);
        List<Beneficiary> assignedBeneficiaries = volunteerTimeDao.getRelatedBeneficiaries(volunteer.getDni());
        Map<LocalDateTime,LocalDateTime> mapa = volunteerTimeDao.getVolunteerTime(volunteer.getDni());
        model.addAttribute("mapa", mapa);
        model.addAttribute("assignedBeneficiaries", assignedBeneficiaries);
        return "/volunteer/listBeneficiariesVol";
    }

    @RequestMapping("/modifiedHour")
    public String modifyHour(Model model, HttpSession session){
        if(session.getAttribute("user") == null){
            model.addAttribute("user", new Volunteer());
            return "beneficiary/login";}
        if(! (session.getAttribute("user") instanceof Volunteer)){
            return"redirect:/";
        }
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
        if(session.getAttribute("user") == null){
            model.addAttribute("user", new Volunteer());
            return "beneficiary/login";}
        if(! (session.getAttribute("user") instanceof Volunteer)){
            return"redirect:/";
        }
        Volunteer user = (Volunteer) session.getAttribute("user");
        Volunteer volunteer = volunteerDao.getVolunteerPerUser(user.getUser());
        session.setAttribute("time",beginningTime);
        VolunteerTime volunteerTime = volunteerTimeDao.getVolunteerTime(volunteer.getDni(),LocalDateTime.parse(beginningTime));
        LocalDateTime fechaHoy = LocalDateTime.now();
        String fecha = fechaHoy.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        model.addAttribute("fechaHoy", fecha);
        model.addAttribute("volunteerTime",volunteerTime);
        return "/volunteer/modifyTimeNow";
    }
    @RequestMapping(value="/removeTimeNow/{beginningTime}", method=RequestMethod.GET)
    public String processremoveVolunteerTime(@PathVariable String beginningTime, HttpSession session, Model model) {
        if(session.getAttribute("user") == null){
            model.addAttribute("user", new Volunteer());
            return "beneficiary/login";}
        if(! (session.getAttribute("user") instanceof Volunteer)){
            return"redirect:/";
        }
        Volunteer user = (Volunteer) session.getAttribute("user");
        Volunteer volunteer = volunteerDao.getVolunteerPerUser(user.getUser());
        session.setAttribute("time",beginningTime);
        volunteerTimeDao.deleteVolunteerTime(volunteer.getDni(),LocalDateTime.parse(beginningTime));
        List<Beneficiary> assignedBeneficiaries = volunteerTimeDao.getRelatedBeneficiaries(volunteer.getDni());
        Map<LocalDateTime,LocalDateTime> mapa = volunteerTimeDao.getVolunteerTime(volunteer.getDni());
        model.addAttribute("mapa", mapa);
        model.addAttribute("assignedBeneficiaries", assignedBeneficiaries);
        return "/volunteer/listBeneficiariesVol";
    }
    @RequestMapping(value="/update", method=RequestMethod.POST)
    public String processUpdate(@ModelAttribute("volunteerTime") VolunteerTime volunteerTime, HttpSession session, Model model) {
        if(session.getAttribute("user") == null){
            model.addAttribute("user", new Volunteer());
            return "beneficiary/login";}
        if(! (session.getAttribute("user") instanceof Volunteer)){
            return"redirect:/";
        }
        volunteerTime.setDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        Volunteer user = (Volunteer) session.getAttribute("user");
        Volunteer volunteer = volunteerDao.getVolunteerPerUser(user.getUser());
        volunteerTime.setDniVolunteer(volunteer.getDni());
        volunteerTime.setDniBeneficiary(null);
        volunteerTimeDao.updateTime(volunteer.getDni(),LocalDateTime.parse(session.getAttribute("time").toString()),volunteerTime.getBeginningTime(),volunteerTime.getEndingTime());

        List<Beneficiary> assignedBeneficiaries = volunteerTimeDao.getRelatedBeneficiaries(volunteer.getDni());
        Map<LocalDateTime,LocalDateTime> mapa = volunteerTimeDao.getVolunteerTime(volunteer.getDni());
        model.addAttribute("mapa", mapa);
        model.addAttribute("assignedBeneficiaries", assignedBeneficiaries);

        return "/volunteer/listBeneficiariesVol";
    }
    @RequestMapping(value="/remove/{dni}", method=RequestMethod.GET)
    public String processRemoveSubmit(@PathVariable String dni,HttpSession session, Model model) {
        if(session.getAttribute("user") == null){
            model.addAttribute("user", new Volunteer());
            return "beneficiary/login";}
        if(! (session.getAttribute("user") instanceof Volunteer)){
            return"redirect:/";
        }

        Volunteer user = (Volunteer)session.getAttribute("user");
        Volunteer volunteer = volunteerDao.getVolunteerPerUser(user.getUser());
        volunteerTimeDao.deleteVol(volunteer.getDni(),dni);
        List<Beneficiary> assignedBeneficiaries = volunteerTimeDao.getRelatedBeneficiaries(volunteer.getDni());
        Map<LocalDateTime,LocalDateTime> mapa = volunteerTimeDao.getVolunteerTime(volunteer.getDni());
        model.addAttribute("mapa", mapa);
        model.addAttribute("assignedBeneficiaries", assignedBeneficiaries);

        /*Request request = new Request();
        request.setId(id.incrementAndGet());
        Beneficiary user = (Beneficiary)session.getAttribute("user");
        request.setDniBeneficiary(userDao.getBeneficiaryPerNom(user.getUser()).getDni());
        request.setTypeOfService(session.getAttribute("tipo").toString());*/
        //session.setAttribute("id",contractDao.getContractByCompany(cif));
       /* request.setSchedule(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        request.setRequestState(RequestState.valueOf("processing"));
        request.setDateAccept(null);
        request.setDateReject(null);
        request.setComment();*/
        //model.addAttribute("request", new Request());
//        return "volunteer/indexVolunteer";
        return "/volunteer/listBeneficiariesVol";


    }

    @RequestMapping(value="/veure/{dni}", method=RequestMethod.GET)
    public String processveureSubmit(@PathVariable String dni,HttpSession session, Model model) {
        if(session.getAttribute("user") == null){
            model.addAttribute("user", new Volunteer());
            return "beneficiary/login";}
        if(! (session.getAttribute("user") instanceof Volunteer)){
            return"redirect:/";
        }

        Volunteer user = (Volunteer)session.getAttribute("user");
        Volunteer volunteer = volunteerDao.getVolunteerPerUser(user.getUser());
        Beneficiary ben = userDao.getBeneficiary(dni);
        VolunteerTime volunteerTime = volunteerTimeDao.getBeneficiaryTime(volunteer.getDni(),dni);
        model.addAttribute("hourIn",volunteerTime.getBeginningTime());
        model.addAttribute("hourOf",volunteerTime.getEndingTime());
        model.addAttribute("ben",ben);


        /*Request request = new Request();
        request.setId(id.incrementAndGet());
        Beneficiary user = (Beneficiary)session.getAttribute("user");
        request.setDniBeneficiary(userDao.getBeneficiaryPerNom(user.getUser()).getDni());
        request.setTypeOfService(session.getAttribute("tipo").toString());*/
        //session.setAttribute("id",contractDao.getContractByCompany(cif));
       /* request.setSchedule(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        request.setRequestState(RequestState.valueOf("processing"));
        request.setDateAccept(null);
        request.setDateReject(null);
        request.setComment();*/
        //model.addAttribute("request", new Request());
        return "volunteer/detalles";

    }


}