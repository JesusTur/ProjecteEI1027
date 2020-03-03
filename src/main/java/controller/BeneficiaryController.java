package controller;

import dao.BeneficiaryDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/beneficiary")
public class BeneficiaryController {

    private BeneficiaryDao beneficiaryDao;


}
