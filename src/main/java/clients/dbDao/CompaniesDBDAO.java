package clients.dbDao;

import clients.exceptions.CustomExceptions;
import clients.exceptions.EnumExceptions;
import clients.beans.Company;
import clients.beans.Coupon;
import clients.dao.CompaniesDAO;
import clients.db.DBManager;
import clients.db.DBTools;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yoav Hachmon, Guy Endvelt and Gery Glazer
 * 03.2022
 */
public class CompaniesDBDAO implements CompaniesDAO {
    CouponsDBDAO couponsDBDAO = new CouponsDBDAO();

    /**
     * Checks the existence of a company in database by company ID
     *
     * @param id input company id
     * @return true or false
     */
    public boolean isCompanyExistsById(int id) {
        Map<Integer, Object> values = new HashMap<>();
        try {
            values.put(1, id);
            ResultSet resultSet = DBTools.runQueryForResult(DBManager.COUNT_COMPANY_BY_ID, values);
            assert resultSet != null;
            resultSet.next();
            return (resultSet.getInt(1) == 1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Checks the existence of a company in database by company`s name
     *
     * @param name input company name
     * @return true or false
     */
    public boolean isCompanyExistsByName(String name) {
        Map<Integer, Object> values = new HashMap<>();
        try {
            values.put(1, name);
            ResultSet resultSet = DBTools.runQueryForResult(DBManager.COUNT_COMPANY_BY_NAME, values);
            assert resultSet != null;
            resultSet.next();
            return (resultSet.getInt(1) == 1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Checks the existence of a company in database by company`s e-mail address
     *
     * @param email input e-mail address
     * @return true or false
     */
    public boolean isCompanyExistsByEmail(String email) {
        Map<Integer, Object> values = new HashMap<>();
        try {
            values.put(1, email);
            ResultSet resultSet = DBTools.runQueryForResult(DBManager.COUNT_COMPANY_BY_EMAIL, values);
            assert resultSet != null;
            resultSet.next();
            return (resultSet.getInt(1) == 1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * * Checks the existence of a company in database by company email and password
     * * @param email input e-mail address
     * * @param password input password
     *
     * @return true or false
     */
    @Override
    public boolean isCompanyExists(String email, String password) {
        Map<Integer, Object> values = new HashMap<>();
        try {
            values.put(1, email);
            values.put(2, password);
            ResultSet resultSet = DBTools.runQueryForResult(DBManager.COUNT_COMPANY_BY_PASS_AND_EMAIL, values);
            assert resultSet != null;
            resultSet.next();
            return (resultSet.getInt(1) == 1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * insert new company info into database
     *
     * @param company input company object
     * @throws CustomExceptions alerts user if name or email already exist in database
     */
    @Override
    public void addCompany(Company company) throws CustomExceptions {
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, company.getName());
        values.put(2, company.getEmail());
        values.put(3, company.getPassword());
        if (isCompanyExistsByName(company.getName())) {
            throw new CustomExceptions(EnumExceptions.NAME_EXIST);
        } else if (isCompanyExistsByEmail(company.getEmail())) {
            throw new CustomExceptions(EnumExceptions.EMAIL_EXIST);
        } else {
            DBTools.runQuery(DBManager.ADD_COMPANY, values);
        }
    }

    /**
     * update company info into database
     *
     * @param company input company object
     * @throws CustomExceptions alerts user if company id not found in database,
     *                          also if user tries to update  name or email and one of them already exist in database
     */
    @Override
    public void updateCompany(Company company) throws CustomExceptions {
        if (!isCompanyExistsById(company.getId())) {
            throw new CustomExceptions(EnumExceptions.ID_NOT_EXIST);
        } else if (isCompanyExistsByEmail(company.getEmail())) {
            throw new CustomExceptions(EnumExceptions.EMAIL_EXIST);
        } else {
            Map<Integer, Object> values = new HashMap<>();
            values.put(1, company.getName());
            values.put(2, company.getEmail());
            values.put(3, company.getPassword());
            DBTools.runQuery(DBManager.UPDATE_COMPANY, values);
        }
    }

    /**
     * remove company and it's coupons from all database
     *
     * @param companyId input company id for removal
     * @throws CustomExceptions alerts user if company id not found in database
     */
    @Override
    public void deleteCompany(int companyId) throws CustomExceptions {
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, companyId);
        if (getOneCompany(companyId) == null) {
            throw new CustomExceptions(EnumExceptions.ID_NOT_EXIST);
        }
        DBTools.runQuery(DBManager.DELETE_COMPANY, values);
    }

    /**
     * gets list of all companies from database
     *
     * @return list of companies
     * @throws CustomExceptions alerts user if database is empty
     */
    public List<Company> getAllCompanies() throws CustomExceptions {
        List<Company> allCompanies = new ArrayList<>();
        ResultSet resultSet = DBTools.runQueryForResult(DBManager.GET_ALL_COMPANIES, new HashMap<>());
        try {
            while (resultSet.next()) {
                Company company = getOneCompany(resultSet.getInt("id"));
                allCompanies.add(company);
            }
        } catch (SQLException | CustomExceptions err) {
            System.out.println(err.getMessage());
        }
        return allCompanies;
    }

    /**
     * retrieve one company from database by ID
     *
     * @param companyId input company ID
     * @return company object info
     * @throws CustomExceptions alerts user if database is empty
     */
    @Override
    public Company getOneCompany(int companyId) throws CustomExceptions {
        if (isCompanyExistsById(companyId)) {
        Company company = null;
        List<Coupon> coupons = null;
        Map<Integer, Object> value = new HashMap<>();
        value.put(1, companyId);
            try {
                coupons = couponsDBDAO.getCouponsByCompanyId(companyId);
                ResultSet resultSet = DBTools.runQueryForResult(DBManager.GET_SINGLE_COMPANY, value);
                assert resultSet != null;
                if (resultSet.next()) {
                    company = new Company(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            coupons
                    );
                }
            } catch (SQLException err) {
                System.out.println(err.getMessage());
            }
            return company;
        } else {
            throw new CustomExceptions(EnumExceptions.NO_COMPANY);
        }
    }

//    /**
//     * retrieve list of company coupons from database by id
//     *
//     * @param companyId input company ID
//     * @return list of coupons
//     */
//    public List<Coupon> getCompanyCoupons(int companyId) {
//        Map<Integer, Object> value = new HashMap<>();
//        value.put(1, companyId);
//        try {
//            if (couponsDBDAO.getCouponsByCompanyId(companyId) == null) {
//                throw new CustomExceptions(EnumExceptions.NO_COUPONS);
//            }
//            return couponsDBDAO.getCouponsByCompanyId(companyId);
//        } catch (CustomExceptions customExceptions) {
//            System.out.println(customExceptions.getMessage());
//            return null;
//        }
//    }
}
