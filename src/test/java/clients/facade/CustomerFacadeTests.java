package clients.facade;

import clients.beans.Category;
import clients.beans.Coupon;
import clients.beans.Customer;
import clients.dbDao.CouponsDBDAO;
import clients.dbDao.CustomersDBDAO;
import clients.exceptions.CustomExceptions;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yoav Hacmon, Guy Endvelt and Gery Glazer
 * 03.2022
 */

/**
 * Customer facade tests
 */
public class CustomerFacadeTests {
    private static CustomersDBDAO customersDBDAO;
    private static Customer customer;
    private static CouponsDBDAO couponsDBDAO;
    private static CustomerFacade customerFacade;;
    private static LoginManager loginManager;
    private static Coupon coupon;


    @BeforeClass
    public static void init() throws CustomExceptions {
        System.out.println("Starting tests for customer facade");
        customerFacade = new CustomerFacade();
        customersDBDAO = new CustomersDBDAO();
        loginManager = LoginManager.getInstance();
        couponsDBDAO = new CouponsDBDAO();
        customer = customersDBDAO.getOneCustomer(3);
        try {
        coupon = couponsDBDAO.getOneCoupon(2);
        customerFacade = (CustomerFacade) loginManager.login("taltul@gmail.com", "101010", ClientType.CUSTOMER);
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test
    public void loginPass() {
            Assert.assertTrue((loginManager.login(customer.getEmail(), customer.getPassword(), ClientType.CUSTOMER) instanceof CustomerFacade));
    }

    @Test
    public void loginFail() {
        loginManager.login("zeev-email", "zeev-password", ClientType.CUSTOMER);
    }

    @Test
    public void purchaseCoupon() throws CustomExceptions {
        customerFacade.purchaseCoupon(coupon);
    }

    @Test
    public void getCustomerCoupons() throws CustomExceptions {
        customerFacade.getCustomerCoupons().forEach(System.out::println);
        Assert.assertEquals(customerFacade.getCustomerCoupons().get(0).getId(), 2);
    }

    @Test
    public void getCustomerCouponsByCategory() {
        try {
            customerFacade.getCustomerCoupons(Category.OUTDOOR).forEach(System.out::println);
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }
    @Test
    public void getCustomerCouponsByCategoryNull() {
        try {
            customerFacade.getCustomerCoupons(Category.ELECTRICITY);
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test
    public void getCustomerCouponsByPrice() throws CustomExceptions {
        customerFacade.getCustomerCoupons(50).forEach(System.out::println);
    }

    @Test
    public void getEmptyCustomerCouponsByPrice() {
        try {
            customerFacade.getCustomerCoupons(10).forEach(System.out::println);
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test
    public void getCustomerDetails() {
        Customer customerDetails = null;
        try {
        customerDetails = customerFacade.getCustomerDetails();
        System.out.println(customerDetails);
        System.out.println(customer);
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }
}
