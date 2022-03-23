package clients.facade;

import clients.beans.Category;
import clients.beans.Coupon;
import clients.dbDao.CouponsDBDAO;
import clients.exceptions.CustomExceptions;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Date;
/**
 * @author Yoav Hachmon, Guy Endvelt and Gery Glazer
 * 03.2022
 */

/**
 * Company facade tests
 */
public class CompanyFacadeTests {
    private static CompanyFacade companyFacade;
    private static Coupon[] coupons = new Coupon[2];
    private static LoginManager loginManager;

    @BeforeClass
    public static void init() {
        System.out.println("Starting tests for company facade");
        companyFacade = new CompanyFacade();

        coupons[0] = new Coupon(1, 1, Category.ELECTRICITY, "bibi's coupon'S", "dont know",
                new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000), 150, 25, "image");
        coupons[1] = new Coupon(2, 1, Category.OUTDOOR, "zeev's coupon'S", "know",
                new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000), 150, 50, "image");
        loginManager = LoginManager.getInstance();
            companyFacade=(CompanyFacade)loginManager.login("sam@sung.com", "s2m5un6", ClientType.COMPANY );
    }

    @Test
    public void loginPass() {
            Assert.assertTrue(loginManager.login("sam@sung.com", "s2m5un6", ClientType.COMPANY)instanceof CompanyFacade);
    }

    @Test
    public void loginFail() {
            loginManager.login("shachar@yaks.com", "pjj123", ClientType.CUSTOMER);
    }

    @Test
    public void addCoupon() {
        try {
            companyFacade.addCoupon(coupons[0]);
            companyFacade.addCoupon(coupons[1]);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void addCouponException() throws CustomExceptions{
        companyFacade.addCoupon(coupons[0]);
    }

    @Test
    public void updateCoupon() {
        try {
            coupons[0].setCategory(Category.ENTERTAINMENT);
            companyFacade.updateCoupon(coupons[0]);
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test
    public void deleteCoupon() {
        try {
            companyFacade.deleteCoupon(1);
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test
    public void getCompanyCoupons() {
        System.out.println(companyFacade.getCompanyCoupons());
    }

    @Test
    public void getCompanyCouponsByCategory() {
        System.out.println(companyFacade.getCompanyCoupons(Category.OUTDOOR));
    }

    @Test
    public void getCompanyCouponsByPrice() {
        System.out.println(companyFacade.getCompanyCoupons(50));
    }

    @Test
    public void getCompanyDetails() {
        System.out.println(companyFacade.getCompanyDetails());
    }
}
