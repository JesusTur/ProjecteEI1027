package es.projecteEI1027.controller;

import es.projecteEI1027.dao.*;
import es.projecteEI1027.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/cas")
public class CasController {
    private CASDao casDao;
    private BeneficiaryDao beneficiaryDao;
    private CompanyDao companyDao;
    private ContractDao contractDao;
    private VolunteerDao volunteerDao;
    private  RequestDao requestDao;

    @Autowired
    public void setBeneficiaryDao(BeneficiaryDao beneficiaryDao) {
        this.beneficiaryDao=beneficiaryDao;
    }
    @Autowired
    public void setCasDao(CASDao casDao){this.casDao=casDao;}
    @Autowired
    public  void setCompanyDao(CompanyDao companyDao){this.companyDao=companyDao;}
    @Autowired
    public  void setContractDao(ContractDao contractDao){this.contractDao=contractDao;}
    @Autowired
    public void setVolunteerDao(VolunteerDao volunteerDao){this.volunteerDao=volunteerDao;}
    @Autowired
    public void setRequestDao(RequestDao requestDao) {
        this.requestDao = requestDao;
    }

    @RequestMapping(value = "/addCompany")
    public String addCompany(Model model, HttpSession session){
        if(session.getAttribute("user") == null){
            model.addAttribute("user", new CAS());
            return "beneficiary/login";}
        if(! (session.getAttribute("user") instanceof CAS)){
            return"redirect:/";
        }
        CAS user = (CAS) session.getAttribute("user");
        if(!(user.getUser().equals("casManager"))) {
            return"redirect:/";
        }


        model.addAttribute("company", new Company());
        ServiceType[] types = ServiceType.values();
        model.addAttribute("types", types);
        return "cas/company/addCompany";
    }
    @RequestMapping(value="/addCompany", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("company") Company company, Model model, BindingResult bindingResult, HttpSession session){
        if(bindingResult.hasErrors()){
            ServiceType[] types = ServiceType.values();
            model.addAttribute("types", types);
            return "cas/company/addCompany";
        }
        company.setRegisteredDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        System.out.println(company.getRegisteredDate());
        companyDao.addCompany(company);
        Contract contract = new Contract();
        model.addAttribute("contract", contract);
        session.setAttribute("company", company);
        return "cas/company/addContract";

    }
    @RequestMapping(value = "/addContract", method = RequestMethod.POST)
    public String addContract(@ModelAttribute("contract") Contract contract, HttpSession session, Model model){
        if(session.getAttribute("user") == null){
            model.addAttribute("user", new CAS());
            return "beneficiary/login";}
        if(! (session.getAttribute("user") instanceof CAS)){
            return"redirect:/";
        }
        CAS user = (CAS) session.getAttribute("user");
        if(!(user.getUser().equals("casManager"))) {
            return"redirect:/";
        }
        Company company = (Company) session.getAttribute("company");
        contract.setCif(company.getCif());
        contract.setTypeOfService(company.getServiceType());
        contract.setStartDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        int id = contractDao.getMAXId() + 1;
        contract.setId(id);
        contract.setPriceUnit(100f);
        System.out.println(contract.getCif());
        contractDao.addContract(contract);
        return "cas/company/registerCompany.html";
    }
    @RequestMapping(value = "/acceptVolunteer")
    public String acceptVolunteer(Model model, HttpSession session){
        if(session.getAttribute("user") == null){
            model.addAttribute("user", new CAS());
            return "beneficiary/login";}
        if(! (session.getAttribute("user") instanceof CAS)){
            return"redirect:/";
        }
        CAS user = (CAS) session.getAttribute("user");
        if(!(user.getUser().equals("casVolunteer"))) {
            return"redirect:/";
        }
        List<Volunteer> volunteers = volunteerDao.getUnacceptedVolunteers();
        model.addAttribute("volunteers", volunteers);
        return"cas/volunteer/acceptVolunteer";
    }
    @RequestMapping(value = "/acceptVolunteer/{dni}", method=RequestMethod.GET)
    public String processAcceptVolunteerSubmit(@PathVariable String dni, Model model, HttpSession session) {
        if(session.getAttribute("user") == null){
            model.addAttribute("user", new CAS());
            return "beneficiary/login";}
        if(! (session.getAttribute("user") instanceof CAS)){
            return"redirect:/";
        }
        CAS user = (CAS) session.getAttribute("user");
        if(!(user.getUser().equals("casVolunteer"))) {
            return"redirect:/";
        }
        Volunteer volunteer = volunteerDao.getVolunteer(dni);
        volunteer.setAccepted(true);
        volunteer.setAcceptationDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        volunteerDao.acceptVolunteer(volunteer);
        System.out.println(volunteerDao.getVolunteer(dni).getAccepted());
        return"cas/volunteer/indexCasVolunteer";
    }
    @RequestMapping(value = "/listRequests")
    public String listRequest(Model model, HttpSession session){
        if(session.getAttribute("user") == null){
            model.addAttribute("user", new CAS());
            return "beneficiary/login";}
        if(! (session.getAttribute("user") instanceof CAS)){
            return"redirect:/";
        }
        CAS user = (CAS) session.getAttribute("user");
        if(!(user.getUser().equals("casCommitee"))) {
            return"redirect:/";
        }
        List<Request> req = requestDao.getPendentRequests();
        HashMap<Beneficiary, Request> requests = new HashMap<>();
        for(Request r : req){
            Beneficiary beneficiary = beneficiaryDao.getBeneficiary(r.getDniBeneficiary());
            requests.put(beneficiary, r);
        }
        model.addAttribute("requests", requests);
        return "cas/committe/pendingRequests";
    }
    @RequestMapping(value = "/denegar/{id}")
    public String denegarServei(@PathVariable int id, Model model, HttpSession session){
        if(session.getAttribute("user") == null){
            model.addAttribute("user", new CAS());
            return "beneficiary/login";}
        if(! (session.getAttribute("user") instanceof CAS)){
            return"redirect:/";
        }
        CAS user = (CAS) session.getAttribute("user");
        if(!(user.getUser().equals("casCommitee"))) {
            return"redirect:/";
        }
        Request request = requestDao.getRequest(id);
        request.setRequestState(RequestState.rejected);
        request.setDateReject(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        requestDao.rejectRequest(request.getId());
        List<Request> req = requestDao.getPendentRequests();
        HashMap<Beneficiary, Request> requests = new HashMap<>();
        for(Request r : req){
            Beneficiary beneficiary = beneficiaryDao.getBeneficiary(r.getDniBeneficiary());
            requests.put(beneficiary, r);
        }
        model.addAttribute("requests", requests);
        return "cas/committe/pendingRequests";
    }
    @RequestMapping(value = "/mostrarCompanyia/{id}")
    public String  mostrarCompanyies(@PathVariable int id, Model model, HttpSession session){
        if(session.getAttribute("user") == null){
            model.addAttribute("user", new CAS());
            return "beneficiary/login";}
        if(! (session.getAttribute("user") instanceof CAS)){
            return"redirect:/";
        }
        CAS user = (CAS) session.getAttribute("user");
        if(!(user.getUser().equals("casCommitee"))) {
            return"redirect:/";
        }
        Request request = requestDao.getRequest(id);
        List<Company> companies = companyDao.getCompaniesPerTipus(request.getTypeOfService());
        HashMap<Company, Contract> comcon = new HashMap<>();
        for( Company c : companies){
            comcon.put(c, contractDao.getContractbyCif(c.getCif()));
        }
        model.addAttribute("request", request);
        model.addAttribute("comcon", comcon);
        session.setAttribute("request", request);
        return"cas/committe/afegirCompanyia";
    }
    @RequestMapping(value ="/acceptarServei/{id}")
    public String acceptarServei(@PathVariable int id, Model model, HttpSession session){
        if(session.getAttribute("user") == null){
            model.addAttribute("user", new CAS());
            return "beneficiary/login";}
        if(! (session.getAttribute("user") instanceof CAS)){
            return"redirect:/";
        }
        CAS user = (CAS) session.getAttribute("user");
        if(!(user.getUser().equals("casCommitee"))) {
            return"redirect:/";
        }
        Request request = (Request) session.getAttribute("request");
        request.setRequestState(RequestState.accepted);
        request.setContractid(id);
        request.setDateAccept(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        contractDao.updateQuantity(id);
        requestDao.updateRequest(request);
        List<Request> req = requestDao.getPendentRequests();
        HashMap<Beneficiary, Request> requests = new HashMap<>();
        for(Request r : req){
            Beneficiary beneficiary = beneficiaryDao.getBeneficiary(r.getDniBeneficiary());
            requests.put(beneficiary, r);
        }
        model.addAttribute("requests", requests);
        return "cas/committe/pendingRequests";
    }
    @RequestMapping(value = "/tornarIndexCom")
    public String tornarIndexCom(){
        return "cas/committe/indexCommitte";
    }
    @RequestMapping(value = "/tornarIndexComp")
    public String tornarIndexComp(){
        return "cas/company/registerCompany";
    }
    @RequestMapping(value = "/tornarIndexV")
    public String tornarIndexV(){
        return "cas/volunteer/indexCasVolunteer";
    }
}
