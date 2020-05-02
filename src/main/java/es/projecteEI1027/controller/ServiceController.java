package es.projecteEI1027.controller;

import com.sun.scenario.effect.impl.sw.java.JSWBlend_SRC_OUTPeer;
import es.projecteEI1027.dao.BeneficiaryDao;
import es.projecteEI1027.dao.CompanyDao;
import es.projecteEI1027.dao.ContractDao;
import es.projecteEI1027.dao.RequestDao;
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
import java.sql.Types;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class ServiceController {
    @Autowired
    private BeneficiaryDao userDao;
    static private AtomicInteger id;
    @Autowired
    private ContractDao contractDao;
    @RequestMapping("/services/add")
    public String login(Model model){
        model.addAttribute("tipo", new String());
        return "beneficiary/services";
    }
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
    @RequestMapping(value="/services/add", method=RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("tipo") String tipo,
                                   BindingResult bindingResult,HttpSession session, Model model) {
        Beneficiary user = (Beneficiary)session.getAttribute("user");
        List<Company> listCompany= userDao.getCompanyPerTipus(tipo,user.getUser());
        List<Volunteer> volunteerServices = userDao.getVolunteerPerBen(tipo);
        Map<String,Float> precios = userDao.getPrecioContract(listCompany);
        model.addAttribute("volunteerServices", volunteerServices);
        model.addAttribute("companiesServices", listCompany);
        model.addAttribute("precios",precios);
        model.addAttribute("dni",userDao.getBeneficiaryPerNom(user.getUser()).getDni());
        return "/beneficiary/addServices";

    }
    @RequestMapping(value="/services/add/companyServices/{cif}", method=RequestMethod.GET)
    public String processAddCServicesSubmit(@PathVariable String cif, Model model) {

        /*Request request = new Request();
        request.setId(id.incrementAndGet());
        Beneficiary user = (Beneficiary)session.getAttribute("user");
        request.setDniBeneficiary(userDao.getBeneficiaryPerNom(user.getUser()).getDni());
        request.setTypeOfService(session.getAttribute("tipo").toString());*/
        System.out.println("hola");
        System.out.println(cif);
        System.out.println(contractDao.getContractByCompany(cif));
        model.addAttribute("id",contractDao.getContractByCompany(cif));
       /* request.setSchedule(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        request.setRequestState(RequestState.valueOf("processing"));
        request.setDateAccept(null);
        request.setDateReject(null);
        request.setComment();*/


        return "/beneficiary/addRequest";

    }
    @RequestMapping(value="/services/addRequest")
    public String AddRequest( Model model) {
        model.addAttribute("request", new Request());
        return "beneficiary/addRequest";
    }
    @RequestMapping(value="/services/addRequest", method=RequestMethod.POST)
    public String processAddRequest(@ModelAttribute("request") Request request,
                                            BindingResult bindingResult,HttpSession session, Model model) {
        System.out.println("adios");
        request.setId(id.incrementAndGet());
        Beneficiary user = (Beneficiary)session.getAttribute("user");
        request.setDniBeneficiary(userDao.getBeneficiaryPerNom(user.getUser()).getDni());
        request.setTypeOfService(session.getAttribute("tipo").toString());
        request.setContractid((Integer)session.getAttribute("id"));
        request.setSchedule(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        request.setRequestState(RequestState.valueOf("processing"));
        request.setDateAccept(null);
        request.setDateReject(null);
        request.setComment(request.getComment());
        request.setDateFinal(null);
        return "/";

    }
}
