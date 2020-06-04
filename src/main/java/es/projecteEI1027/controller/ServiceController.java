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
import javax.validation.constraints.Null;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.*;
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
    @Autowired
    private VolunteerDao volunteerDao;

    @RequestMapping(value = "/services", method = RequestMethod.POST)
    public String checkServices(@ModelAttribute("user") String usuario ,BindingResult bindingResult, HttpSession session, Model model){

        if(session.getAttribute("user") == null){
            bindingResult.rejectValue("user", "invalid",
                    "No existeix aquest usuari");
            return "beneficiary/login";
        }
        Beneficiary user = (Beneficiary)session.getAttribute("user");
        List<Volunteer> listVolunteer= userDao.getVolunteerServicesUser(userDao.getBeneficiaryPerNom(user.getUser()).getDni());
        List<Request> listServiceActives= userDao.getServicePerBen(user.getUser());
        List<Request> listServicePending= userDao.getServicePerBenPending(user.getUser());

        if(userDao.yaTiene(userDao.getBeneficiaryPerNom(user.getUser()).getDni())){
            VolunteerTime volunteerTime = userDao.horarios(userDao.getBeneficiaryPerNom(user.getUser()).getDni());
            LocalDateTime horaInit = volunteerTime.getBeginningTime();
            LocalDateTime horaFin = volunteerTime.getEndingTime();
            model.addAttribute("horaInit",horaInit);
            model.addAttribute("horaFin",horaFin);
        }


        //List<Company> listCompany= userDao.getCompanyPerTipus(tipo,user.getUser());
        //Map<String,Float> precios = userDao.getPrecioContract(listCompanyActives);

        model.addAttribute("user",user.getUser());
        model.addAttribute("volunteers",  listVolunteer);
        model.addAttribute("servicesActives", listServiceActives);
        model.addAttribute("servicesPending", listServicePending);
        //model.addAttribute("companiesServices", listCompany);
        //model.addAttribute("precios",precios);
        return "beneficiary/listServices";

    }
    @RequestMapping("/services/add")
    public String login(Model model){
        model.addAttribute("tipo", new String());
        return "beneficiary/services";
    }
    @RequestMapping(value="/services/add", method=RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("tipo") String tipo,
                                   HttpSession session, Model model) {
        Beneficiary user = (Beneficiary)session.getAttribute("user");
        //List<Company> listCompany= userDao.getCompanyPerTipus(tipo,user.getUser());
        //List<Volunteer> volunteerServices = userDao.getVolunteerPerBen(userDao.getBeneficiaryPerNom(user.getUser()).getDni());
        //Map<String,Float> precios = userDao.getPrecioContract(listCompany);
        session.setAttribute("tipo", tipo);
        //model.addAttribute("volunteerServices", volunteerServices);
        //model.addAttribute("companiesServices", listCompany);
        //model.addAttribute("precios",precios);
        model.addAttribute("dni",userDao.getBeneficiaryPerNom(user.getUser()).getDni());
        if(userDao.servicioPendiente(user.getUser())==null && userDao.servicioMismoTipo(user.getUser(),tipo)==null) {
            Request request = new Request();
            model.addAttribute("request", new Request());
            return "beneficiary/addServices";
        }
        if(userDao.servicioPendiente(user.getUser())!=null){
            return "beneficiary/rejectService";
        }
        return "beneficiary/rejectServiceTipo";

    }
    @RequestMapping(value="/services/addVolunteer")
    public String processAddVolunteer(HttpSession session, Model model) {
        Beneficiary user = (Beneficiary)session.getAttribute("user");
        List<Volunteer> volunteerServices;
        if(userDao.yaTiene(userDao.getBeneficiaryPerNom(user.getUser()).getDni())){
            volunteerServices = new ArrayList<Volunteer>();
        }
        else{
            volunteerServices = userDao.getVolunteerPerBen(userDao.getBeneficiaryPerNom(user.getUser()).getDni());
        }
        model.addAttribute("volunteerServices", volunteerServices);
        model.addAttribute("dni",userDao.getBeneficiaryPerNom(user.getUser()).getDni());
        return "beneficiary/addVolunteer";

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
        //model.addAttribute("request", new Request());
        return "beneficiary/addRequest";

    }
    @RequestMapping(value="/services/add/volunteerServices/{dni}", method=RequestMethod.GET)
    public String processAddVServicesSubmit(@PathVariable String dni,HttpSession session, Model model) {
        /*Request request = new Request();
        request.setId(id.incrementAndGet());
        Beneficiary user = (Beneficiary)session.getAttribute("user");
        request.setDniBeneficiary(userDao.getBeneficiaryPerNom(user.getUser()).getDni());
        request.setTypeOfService(session.getAttribute("tipo").toString());*/
        Map<LocalDateTime,LocalDateTime> map = new HashMap<>();
        model.addAttribute("mapa",volunteerTimeDao.getVolunteerTime(dni));
        model.addAttribute("tiempoIni",volunteerTimeDao.getVolunteerTimeInit(dni));
        model.addAttribute("tiempoFin",volunteerTimeDao.getVolunteerTimeFinal(dni));
        session.setAttribute("dniVolunteer",dni);
       /* request.setSchedule(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        request.setRequestState(RequestState.valueOf("processing"));
        request.setDateAccept(null);
        request.setDateReject(null);
        request.setComment();*/
        VolunteerTime volunteerTime = new VolunteerTime();
        model.addAttribute("volunteerT", volunteerTime);

        return "beneficiary/addVolunteerTime";

    }
    @RequestMapping(value="/services/addVolunteerTime", method=RequestMethod.POST)
    public String processAddVolunteerTime(@ModelAttribute("volunteerT") VolunteerTime volunteerTime, HttpSession session, Model model) {
        volunteerTime.setDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        volunteerTime.setDniVolunteer(session.getAttribute("dniVolunteer").toString());
        Beneficiary user = (Beneficiary)session.getAttribute("user");
        volunteerTime.setDniBeneficiary(userDao.getBeneficiaryPerNom(user.getUser()).getDni());
        volunteerTime.setAvailable(true);
        volunteerTimeDao.updateTime(session.getAttribute("dniVolunteer").toString(),volunteerTime.getBeginningTime(),volunteerTime.getEndingTime());
        volunteerTimeDao.addVolunteerTime(volunteerTime);
        Volunteer vol = volunteerDao.getVolunteer(session.getAttribute("dniVolunteer").toString());
        model.addAttribute("nombreVolunteer",vol.getName());
        model.addAttribute("horasInit",volunteerTime.getBeginningTime());
        model.addAttribute("horasFin",volunteerTime.getEndingTime());
        return "beneficiary/añadidoVolunteer";

    }
    @RequestMapping(value="/services/addRequest", method=RequestMethod.POST)
    public String processAddRequest(@ModelAttribute("request") Request request,
                                            HttpSession session,Model model) {
        idRequest = new AtomicInteger(requestDao.getRequestid());
        request.setId(idRequest.incrementAndGet());
        Beneficiary user = (Beneficiary)session.getAttribute("user");
        request.setDniBeneficiary(userDao.getBeneficiaryPerNom(user.getUser()).getDni());
        request.setTypeOfService(session.getAttribute("tipo").toString());
        request.setContractid(null);
        request.setSchedule(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        request.setRequestState(RequestState.valueOf("processing"));
        request.setDateAccept(null);
        request.setDateReject(null);
        request.setDateFinal(null);
        requestDao.addRequest(request);
        model.addAttribute("tipo",session.getAttribute("tipo").toString());
        return "beneficiary/servicioSolicitado";
    }

    @RequestMapping(value="/services/remove/{dni}", method=RequestMethod.GET)
    public String processRemoveSubmit(@PathVariable String dni,HttpSession session, Model model) {

        Beneficiary user = (Beneficiary)session.getAttribute("user");
        volunteerTimeDao.deleteVol(dni,userDao.getBeneficiaryPerNom(user.getUser()).getDni());

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
        return "beneficiary/listServices";

    }
    @RequestMapping(value="/services/removeService/{typeOfService}", method=RequestMethod.GET)
    public String processRemoveServiceSubmit(@PathVariable String typeOfService,HttpSession session, Model model) {

        Beneficiary user = (Beneficiary)session.getAttribute("user");
        requestDao.rejectedService(userDao.getBeneficiaryPerNom(user.getUser()).getDni(),typeOfService);

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
        return "beneficiary/listServices";

    }
}
