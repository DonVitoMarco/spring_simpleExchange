package pl.thewalkingcode.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.thewalkingcode.dao.CompanyDao;
import pl.thewalkingcode.model.Company;
import pl.thewalkingcode.service.API.CompanyService;

import javax.transaction.Transactional;


@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDao companyDao;


    public Company findCompany(String code) {
        return companyDao.read(code);
    }

    public void updateUnit(Long unit, String code) {
        Company company = new Company();
        company.setSumUnits(unit);
        company.setCode(code);
        companyDao.update(company);
    }

}