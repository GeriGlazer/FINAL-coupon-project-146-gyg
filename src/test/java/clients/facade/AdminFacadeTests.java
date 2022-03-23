package clients.facade;

import clients.beans.Company;
import clients.beans.Customer;
import clients.exceptions.CustomExceptions;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
/**
 * @author Yoav Hachmon, Guy Endvelt and Gery Glazer
 * 03.2022
 */

/**
 * Administrator facade tests
 */
public class AdminFacadeTests {
    private static LoginManager loginManager;
    private static AdminFacade adminFacade;
    private static Company[] companies = {
            new Company(1, "samsung", "sam@sung.com", "s2m5un6", new ArrayList<>()),
            new Company(2, "sano", "sano@sushi.com", "s@n0", new ArrayList<>()),
            new Company(3, "laline", "lalin@soap.com", "s0@p", new ArrayList<>()),
            new Company(4, "elite", "elite@strauss.com", "el&str", new ArrayList<>()),
            new Company(5, "electra", "electra@hashmal.com", "w@t&$$", new ArrayList<>())
    };
    private static Customer[] customers = {
            new Customer(1, "Dana", "Sercovich", "dana@serco.com", "54321", new ArrayList<>()),
            new Customer(2, "Zeev", "Mindali", "zeev@funfun.com", "54796", new ArrayList<>()),
            new Customer(3, "Tal", "Bechor", "taltul@gmail.com", "101010", new ArrayList<>()),
            new Customer(4, "Omer", "Cohen", "omerfix@yahoo.com", "54391", new ArrayList<>()),
            new Customer(5, "Geri", "Glazer", "gerig@gmail.com", "2022", new ArrayList<>())
    };

    /**
     * fields initialization
     * series of procedures before tests run
     */
    @BeforeClass
    public static void init() {
        adminFacade = new AdminFacade();
        loginManager = LoginManager.getInstance();
        adminFacade = (AdminFacade) loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
    }

    @Test
    public void loginPass() {
        Assert.assertTrue((loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR) instanceof AdminFacade));
    }

    @Test
    public void loginFail() throws CustomExceptions {
        loginManager.login("gery@gmail.com", "14521", ClientType.ADMINISTRATOR);
    }

    @Test
    public void addCompany() {
        try {
            for (Company item : companies) {
                adminFacade.addCompany(item);
            }
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test
    public void addCompanyFail() {
        try {
            adminFacade.addCompany(companies[1]);
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test
    public void updateCompanyPass() {
        companies[2].setPassword("12345");
        try {
            adminFacade.updateCompany(companies[2]);
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test
    public void setCompanyIdFail() {
        try {
            companies[3].setId(20);
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }


    @Test
    public void deleteCompanyPass() throws CustomExceptions {
        adminFacade.deleteCompany(companies[4].getId());
    }

    @Test
    public void deleteCompanyFail() throws CustomExceptions {
        adminFacade.deleteCompany(60);
    }

    @Test
    public void getAllCompanies() {
        try {
            System.out.println(adminFacade.getAllCompanies());
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test
    public void getOneCompanyPass() {
        try {
            System.out.println(adminFacade.getOneCompany(4));
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test
    public void getOneCompanyFail() {
        try {
            System.out.println(adminFacade.getOneCompany(10));
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test
    public void addCustomer() {
        try {
            for (Customer item : customers) {
                adminFacade.addCustomer(item);
            }
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test
    public void addCustomerFail() {
        try {
            adminFacade.addCustomer(customers[2]);
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test
    public void updateCustomerPass() {
        customers[3].setPassword("56789");
        try {
            adminFacade.updateCustomer(customers[3]);
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test
    public void setCustomerIdFail() {
        try {
            customers[4].setId(15);
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test
    public void deleteCustomerPass() {
        try {
            adminFacade.deleteCustomer(4);
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test
    public void deleteCustomerFail() {
        try {
            adminFacade.deleteCustomer(20);
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test
    public void getAllCustomers() {
        try {
            System.out.println(adminFacade.getAllCustomers());
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test
    public void getOneCustomerPass() throws CustomExceptions {
        System.out.println(adminFacade.getOneCustomer(3));
    }

    @Test
    public void getOneCustomerFail() throws CustomExceptions {
        System.out.println(adminFacade.getOneCustomer(4));
    }
}