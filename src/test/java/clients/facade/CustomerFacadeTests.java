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

import java.util.List;

/**
 * @author Yoav Hachmon, Guy Endvelt and Gery Glazer
 * 03.2022
 */

/**
 * Customer facade tests
 */
public class CustomerFacadeTests {
    private static CustomersDBDAO customersDBDAO;
    private static Customer customer;
    private static CouponsDBDAO couponsDBDAO;
    private static CustomerFacade customerFacade;
    private static LoginManager loginManager;
    private static Coupon coupon;


    @BeforeClass
    public static void init() {
        System.out.println("Starting tests for customer facade");
        customersDBDAO = new CustomersDBDAO();
        customer = customersDBDAO.getOneCustomer(1);
        loginManager = LoginManager.getInstance();
        couponsDBDAO = new CouponsDBDAO();
        try {
            coupon = couponsDBDAO.getOneCoupon(1);
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
        try {
            customerFacade = (CustomerFacade) loginManager.login(customer.getEmail(), customer.getPassword(), ClientType.CUSTOMER);
        } catch (CustomExceptions e) {
            e.printStackTrace();
        }

    }

    @Test
    public void loginPass() {
        try {
            Assert.assertTrue((loginManager.login(customer.getEmail(), customer.getPassword(), ClientType.CUSTOMER) instanceof CustomerFacade));
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test
    public void loginFail() {
        try {
            Assert.assertNull(loginManager.login("zeev-email", "zeev-password", ClientType.CUSTOMER));
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test(expected = CustomExceptions.class)
    public void exceptionLogin() throws CustomExceptions {
        loginManager.login("zeev-email", "zeev-password", ClientType.CUSTOMER);
    }

    @Test
    public void purchaseCoupon() throws CustomExceptions {
        customer = customersDBDAO.getOneCustomer(1);
        int beforeAmount = coupon.getAmount();
        customerFacade.purchaseCoupon(coupon);
        coupon = couponsDBDAO.getOneCoupon(1);
        int afterAmount = coupon.getAmount();
        Assert.assertEquals(beforeAmount, afterAmount + 1);
    }

    @Test
    public void getCustomerCoupons() throws CustomExceptions {
        customerFacade.getCustomerCoupons().forEach(System.out::println);
        Assert.assertEquals(customerFacade.getCustomerCoupons().get(0).getId(), 1);
//        Assert.assertEquals(customerFacade.getCustomerCoupons().get(1).getId(), 2);
//        Assert.assertEquals(customerFacade.getCustomerCoupons().get(2).getId(), 5);
//        Assert.assertEquals(customerFacade.getCustomerCoupons().get(3).getId(), 6);
    }

    @Test
    public void getEmptyCustomerCoupons() throws CustomExceptions {
        List<Coupon> couponList = customerFacade.getCustomerCoupons();
    }

    @Test
    public void getCustomerCouponsByCategory() throws CustomExceptions {
        customerFacade.getCustomerCoupons(Category.ELECTRICITY).forEach(System.out::println);
//        Assert.assertEquals(customerFacade.getCustomerCoupons(Category.FOOD).get(0).getId(), 1);
//        Assert.assertEquals(customerFacade.getCustomerCoupons(Category.ENTERTAINMENT).get(0).getId(), 5);
    }

    @Test
    public void getCustomerCouponsByPrice() throws CustomExceptions {
        customerFacade.getCustomerCoupons(50).forEach(System.out::println);
        Assert.assertEquals(customerFacade.getCustomerCoupons(50).get(0).getId(), 1);
    }

    @Test(expected = CustomExceptions.class)
    public void getEmptyCustomerCouponsByPrice() throws CustomExceptions {
        customerFacade.getCustomerCoupons(10).forEach(System.out::println);
    }

    @Test
    public void getCustomerDetails() {
        Customer customerDetails = customerFacade.getCustomerDetails();
        System.out.println(customerDetails);
        System.out.println(customer);
    }
}
