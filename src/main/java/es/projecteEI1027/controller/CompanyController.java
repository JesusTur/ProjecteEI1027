package es.projecteEI1027.controller;

import es.projecteEI1027.dao.BeneficiaryDao;
import es.projecteEI1027.dao.CompanyDao;
import es.projecteEI1027.dao.ContractDao;
import es.projecteEI1027.dao.RequestDao;
import es.projecteEI1027.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/company")
public class CompanyController {
    private CompanyDao companyDao;
    private RequestDao requestDao;
    private ContractDao contractDao;
    private BeneficiaryDao beneficiaryDao;
    @Autowired
    public void setCompanyDao(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }
    @Autowired
    public void setRequestDao(RequestDao requestDao) {
        this.requestDao = requestDao;
    }
    @Autowired
    public void setContractDao(ContractDao contractDao) {
        this.contractDao = contractDao;
    }
    @Autowired
    public void setBeneficiaryDao(BeneficiaryDao beneficiaryDao) {
        this.beneficiaryDao = beneficiaryDao;
    }
    @RequestMapping("/list")
    public String listBeneficiaries(Model model, HttpSession session){
        Company user = (Company) session.getAttribute("user");
        Company company = companyDao.getCompanyPerUser(user.getUser());
        Integer contractId = contractDao.getContractId(company.getCif());
        List<Request> requests = requestDao.getRequestsByContractIde(contractId);
        HashMap<Beneficiary, Request> benReq = new HashMap<>();
        for(Request r: requests){
            Beneficiary beneficiary = beneficiaryDao.getBeneficiary(r.getDniBeneficiary());
            benReq.put(beneficiary, r);
        }
        model.addAttribute("benReq", benReq);
        return "company/listBeneficiaries";
    }
}
