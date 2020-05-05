package es.projecteEI1027.controller;

import com.sun.scenario.effect.impl.sw.java.JSWBlend_SRC_OUTPeer;
import es.projecteEI1027.dao.*;
import es.projecteEI1027.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.parser.Element;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class ServiceController {
    static public AtomicInteger idRequest;
    @Autowired
    private BeneficiaryDao userDao;
    @Autowired
    private ContractDao contractDao;
    @Autowired
    private RequestDao requestDao;
    @Autowired
    private VolunteerTimeDao volunteerTimeDao;

    @RequestMapping(value = "/services", method = RequestMethod.POST)
    public String checkServices(@ModelAttribute("user") String userio ,BindingResult bindingResult, HttpSession session, Model model){

        if(session.getAttribute("user") == null){
            bindingResult.rejectValue("user", "invalid",
                    "No existeix aquest usuari");
            return "beneficiary/login";
        }
        Beneficiary user = (Beneficiary)session.getAttribute("user");
        List<Volunteer> listVolunteer= userDao.getVolunteerServicesUser(userDao.getBeneficiaryPerNom(user.getUser()).getDni());
        List<Company> listCompanyActives= userDao.getCompanyPerBen(user.getUser());
        //List<Company> listCompany= userDao.getCompanyPerTipus(tipo,user.getUser());
        //Map<String,Float> precios = userDao.getPrecioContract(listCompanyActives);
        model.addAttribute("user",user.getUser());
        model.addAttribute("volunteers",  listVolunteer);
        model.addAttribute("companies", listCompanyActives);
        //model.addAttribute("companiesServices", listCompany);
        //model.addAttribute("precios",precios);
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
    @RequestMapping("/services/add")
    public String login(Model model){
        model.addAttribute("tipo", new String());
        return "beneficiary/services";
    }
    @RequestMapping(value="/services/add", method=RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("tipo") String tipo,
                                   BindingResult bindingResult,HttpSession session, Model model) {
        Beneficiary user = (Beneficiary)session.getAttribute("user");
        List<Company> listCompany= userDao.getCompanyPerTipus(tipo,user.getUser());
        List<Volunteer> volunteerServices = userDao.getVolunteerPerBen(tipo,userDao.getBeneficiaryPerNom(user.getUser()).getDni());
        Map<String,Float> precios = userDao.getPrecioContract(listCompany);
        session.setAttribute("tipo", tipo);
        model.addAttribute("volunteerServices", volunteerServices);
        model.addAttribute("companiesServices", listCompany);
        model.addAttribute("precios",precios);
        model.addAttribute("dni",userDao.getBeneficiaryPerNom(user.getUser()).getDni());
        return "/beneficiary/addServices";

    }
    @RequestMapping(value="/services/add/companyServices/{cif}", method=RequestMethod.GET)
    public String processAddCServicesSubmit(@PathVariable String cif,HttpSession session, Model model) {

        /*Request request = new Request();
        request.setId(id.incrementAndGet());
        Beneficiary user = (Beneficiary)session.getAttribute("user");
        request.setDniBeneficiary(userDao.getBeneficiaryPerNom(user.getUser()).getDni());
        request.setTypeOfService(session.getAttribute("tipo").toString());*/
        session.setAttribute("id",contractDao.getContractByCompany(cif));
       /* request.setSchedule(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        request.setRequestState(RequestState.valueOf("processing"));
        request.setDateAccept(null);
        request.setDateReject(null);
        request.setComment();*/
        model.addAttribute("request", new Request());

        return "beneficiary/addRequest";

    }
    @RequestMapping(value="/services/add/volunteerServices/{dni}", method=RequestMethod.GET)
    public String processAddVServicesSubmit(@PathVariable String dni,HttpSession session, Model model) {

        /*Request request = new Request();
        request.setId(id.incrementAndGet());
        Beneficiary user = (Beneficiary)session.getAttribute("user");
        request.setDniBeneficiary(userDao.getBeneficiaryPerNom(user.getUser()).getDni());
        request.setTypeOfService(session.getAttribute("tipo").toString());*/
        model.addAttribute("tiempoIni",volunteerTimeDao.getVolunteerTimeInit(dni));
        model.addAttribute("tiempoFin",volunteerTimeDao.getVolunteerTimeFinal(dni));
        session.setAttribute("dniVolunteer",dni);
       /* request.setSchedule(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        request.setRequestState(RequestState.valueOf("processing"));
        request.setDateAccept(null);
        request.setDateReject(null);
        request.setComment();*/
        model.addAttribute("volunteerT", new VolunteerTime());

        return "beneficiary/addVolunteerTime";

    }
    @RequestMapping(value="/services/addVolunteerTime", method=RequestMethod.POST)
    public String processAddVolunteerTime(@ModelAttribute("volunteerT") VolunteerTime volunteerTime,
                                    BindingResult bindingResult,HttpSession session, Model model) {
        System.out.println(volunteerTime.getBeginningTime());
        System.out.println(volunteerTime.getEndingTime());
        volunteerTime.setDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        volunteerTime.setDniVolunteer(session.getAttribute("dniVolunteer").toString());
        Beneficiary user = (Beneficiary)session.getAttribute("user");
        volunteerTime.setDniBeneficiary(userDao.getBeneficiaryPerNom(user.getUser()).getDni());
        volunteerTime.setAvailable(true);
        volunteerTimeDao.updateTime(session.getAttribute("dniVolunteer").toString(),Timestamp.valueOf(volunteerTime.getBeginningTime()),Timestamp.valueOf(volunteerTime.getEndingTime()));
        volunteerTimeDao.addVolunteerTime(volunteerTime);
        return "beneficiary/addServices";

    }
/*    @RequestMapping(value="/services/addRequest")
    public String AddRequest( Model model) {

        System.out.println("hel");
        return "beneficiary/addRequest";
    }*/

    @RequestMapping(value="/services/addRequest", method=RequestMethod.POST)
    public String processAddRequest(@ModelAttribute("request") Request request,
                                            BindingResult bindingResult,HttpSession session, Model model) {
        idRequest = new AtomicInteger(requestDao.getRequestid());
        request.setId(idRequest.incrementAndGet());
        Beneficiary user = (Beneficiary)session.getAttribute("user");
        request.setDniBeneficiary(userDao.getBeneficiaryPerNom(user.getUser()).getDni());
        request.setTypeOfService(session.getAttribute("tipo").toString());
        request.setContractid((Integer)session.getAttribute("id"));
        request.setSchedule(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        request.setRequestState(RequestState.valueOf("processing"));
        request.setDateAccept(null);
        request.setDateReject(null);
        request.setDateFinal(null);
        requestDao.addRequest(request);
        return "beneficiary/addServices";

    }
}
