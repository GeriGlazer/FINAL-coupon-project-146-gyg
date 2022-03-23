package inProcessTests;

import clients.exceptions.CustomExceptions;
import clients.beans.Coupon;
import clients.beans.Customer;
import clients.dbDao.CouponsDBDAO;
import clients.dbDao.CustomersDBDAO;
import clients.facade.ClientFacade;
import clients.facade.ClientType;
import clients.facade.CustomerFacade;
import clients.facade.LoginManager;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class CustomerTests {
    private static CustomersDBDAO customersDBDAO;
    private static CouponsDBDAO couponsDBDAO;
    private static Customer customer;
    private static LoginManager loginManager;

    @BeforeClass
    public static void message() {
        System.out.println("start test and initialize db-dao");
        customer = new Customer(5, "golan", "klain", "golans email", "golans-password", null);
        customersDBDAO = new CustomersDBDAO();
        couponsDBDAO = new CouponsDBDAO();
        loginManager = LoginManager.getInstance();
    }

    @Test
    public void customerLogin() {
        ClientType customerType = ClientType.CUSTOMER;
        ClientFacade customerFacade = loginManager.login("golans-email", "golans-password", customerType);
        Assert.assertTrue(customerFacade instanceof CustomerFacade);
    }

    @Test
    public void addCustomer() throws CustomExceptions {
        customersDBDAO.addCustomer(customer);
    }

    @Test
    public void customerDoesNotExist() throws CustomExceptions {
        Assert.assertFalse(customersDBDAO.isCustomerExist("customer123", "password123"));
    }

    @Test
    public void customerExist() throws CustomExceptions {
        Assert.assertTrue(customersDBDAO.isCustomerExist("geris-email", "geris-password"));
    }

    @Test
    public void updateCustomer() {
        customer = new Customer(1, "yoav", "hachmon", "yoavs-pemail", "yoavs-password", null);
        customer.setEmail("yoyo123-email");
        try {
            customersDBDAO.updateCustomer(customer);
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test
    public void deleteCustomer() {
        try {
            customersDBDAO.deleteCustomer(3);
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test
    public void getAllCustomers() {
        List<Customer> customerList = customersDBDAO.getAllCustomers();
        customerList.forEach(System.out::println);
    }

    @Test
    public void getOneCustomer() {
        try {
            System.out.println(customersDBDAO.getOneCustomer(4));
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test
    public void getCouponsByCustomerId() throws CustomExceptions {
        List<Coupon> coupons = couponsDBDAO.getCouponsByCustomerId(3);
        System.out.println(coupons);
    }
}
