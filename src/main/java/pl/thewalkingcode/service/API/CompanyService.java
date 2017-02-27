package pl.thewalkingcode.service.API;

import pl.thewalkingcode.model.Company;

public interface CompanyService {

    Company findCompany(String code);

    void updateUnit(Long unit, String code);

}
