package clients.facade;

import clients.beans.Company;
import clients.beans.Customer;
import clients.dbDao.CompaniesDBDAO;
import clients.dbDao.CouponsDBDAO;
import clients.dbDao.CustomersDBDAO;
import clients.exceptions.CustomExceptions;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
/**
 * @author Yoav Hachmon, Guy Endvelt and Gery Glazer
 * 03.2022
 */

/**
 * Administrator facade tests
 */
public class AdminFacadeTests {
    private static CouponsDBDAO couponsDBDAO;
    private static CompaniesDBDAO companiesDBDAO;
    private static CustomersDBDAO customersDBDAO;
    private static LoginManager loginManager;
    private static AdminFacade adminFacade;
    private static Company company = new Company(1, "samsung", "sam@sung.com", "s2m5un6", new ArrayList<>());
    private static Customer customer = new Customer(1, "Dana", "Sercovich", "dana@serco.com", "54321", new ArrayList<>());

    /**
     * fields initialization
     * series of procedures before tests run
     */
    @BeforeClass
    public static void init() {
        System.out.println("Starting tests for Administrator facade");
        customersDBDAO = new CustomersDBDAO();
        companiesDBDAO = new CompaniesDBDAO();
        couponsDBDAO = new CouponsDBDAO();
        loginManager = LoginManager.getInstance();
    }

    @Test
    public void loginPass() {
        try {
            Assert.assertTrue((loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR) instanceof AdminFacade));
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test(expected = CustomExceptions.class)
    public void loginFail() throws CustomExceptions {
            Assert.assertTrue((loginManager.login("gery@gmail.com", "14521", ClientType.ADMINISTRATOR) instanceof AdminFacade));
    }

    @Test
    public void addCompany() {
        try {
            companiesDBDAO.addCompany(company);
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test
    public void addedCompanyPass() {
        assertTrue(companiesDBDAO.isCompanyExists("sam@sung.com", "s2m5un6"));
        assertTrue(companiesDBDAO.isCompanyExistsById(1));
        assertTrue(companiesDBDAO.isCompanyExistsByEmail("sam@sung.com"));
        assertTrue(companiesDBDAO.isCompanyExistsByName("samsung"));
    }

    @Test
    public void addedCompanyFail() {
        assertFalse(companiesDBDAO.isCompanyExists("sam@sam.com", "s2m5un6"));
        assertFalse(companiesDBDAO.isCompanyExists("sam@sung.com", "s11116"));
        assertFalse(companiesDBDAO.isCompanyExistsById(10));
        assertFalse(companiesDBDAO.isCompanyExistsByEmail("som@sung.com"));
        assertFalse(companiesDBDAO.isCompanyExistsByName("sammsung"));
    }

    @Test
    public void updateCompanyPass() {
        company.setPassword("12345");
        try {
            companiesDBDAO.updateCompany(company);
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
         }
    }

    @Test(expected = CustomExceptions.class)
    public void updateCompanyFail() throws CustomExceptions {
            company.setId(20);
            companiesDBDAO.updateCompany(company);
    }

    @Test
    public void deleteCompanyPass() {
        try {
            companiesDBDAO.deleteCompany(company.getId());
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test(expected = CustomExceptions.class)
    public void deleteCompanyFail() {
        try {
            companiesDBDAO.deleteCompany(2);
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test
    public void getAllCompanies() {
        try {
            System.out.println(companiesDBDAO.getAllCompanies());
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test
    public void getOneCompanyPass() {
        try {
            System.out.println(companiesDBDAO.getOneCompany(6));
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test(expected = CustomExceptions.class)
    public void getOneCompanyFail() {
        try {
            System.out.println(companiesDBDAO.getOneCompany(10));
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test
    public void addCustomer() {
        try {
            customersDBDAO.addCustomer(customer);
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test
    public void addedCustomerPass() {
        assertTrue(customersDBDAO.isCustomerExist("dana@serco.com", "54321"));
        assertTrue(customersDBDAO.isCustomerExistsById("dana@serco.com"));
    }

    @Test
    public void addedCustomerFail() {
        assertFalse(customersDBDAO.isCustomerExist("oren@serco.com", "54321"));
        assertFalse(customersDBDAO.isCustomerExist("dana@serco.com", "52341"));
        assertFalse(customersDBDAO.isCustomerExistsById("dana@serc.com"));
    }

    @Test
    public void updateCustomerPass() {
        customer.setPassword("56789");
        try {
            customersDBDAO.updateCustomer(customer);
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test(expected = CustomExceptions.class)
    public void updateCustomerFail() {
        try {
            customer.setId(15);
            customersDBDAO.updateCustomer(customer);
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test
    public void deleteCustomerPass() {
        try {
            customersDBDAO.deleteCustomer(2);
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test(expected = CustomExceptions.class)
    public void deleteCustomerFail() {
        try {
            customersDBDAO.deleteCustomer(20);
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test
    public void getAllCustomers() {
        System.out.println(customersDBDAO.getAllCustomers());
    }

    @Test
    public void getOneCustomerPass() {
        System.out.println(customersDBDAO.getOneCustomer(1));
    }

    @Test(expected = CustomExceptions.class)
    public void getOneCustomerFail() {
        customersDBDAO.getOneCustomer(20);
    }
}