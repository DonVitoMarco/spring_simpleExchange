package pl.thewalkingcode.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pl.thewalkingcode.model.Company;

import java.sql.ResultSet;
import java.sql.SQLException;


@Repository("companyDao")
public class CompanyDao implements GenericDao<Company, String> {

    private JdbcTemplate jdbcTemplate;

    private static final String READ = "SELECT * FROM companies WHERE id_code = ?";
    private static final String UPDATE_UNIT = "UPDATE companies SET unit = ? WHERE id_code = ?";

    @Autowired
    public CompanyDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Company company) {

    }

    @Override
    public void update(Company company) {
        Company temp = read(company.getCode());
        if(!temp.getSumUnits().equals(company.getSumUnits())) {
            this.jdbcTemplate.update(UPDATE_UNIT, company.getSumUnits(), company.getCode());
        }
    }

    @Override
    public Company read(String code) {
        Company company = this.jdbcTemplate.queryForObject(READ, new Object[]{code},
                new RowMapper<Company>() {
                    @Override
                    public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Company company = new Company();
                        company.setCode(rs.getString("ID_CODE"));
                        company.setSumUnits(rs.getLong("UNIT"));
                        return company;
                    }
                });
        return company;
    }

}
