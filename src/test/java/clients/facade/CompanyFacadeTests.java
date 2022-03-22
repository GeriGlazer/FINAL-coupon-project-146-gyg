package clients.facade;

import clients.beans.Category;
import clients.beans.Coupon;
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
    private static Coupon coupon;
    private static LoginManager loginManager;

    @BeforeClass
    public static void init() {
        System.out.println("Starting tests for company facade");
        companyFacade = new CompanyFacade();
        coupon = new Coupon(1, 2, Category.ELECTRICITY, "bibi's coupon'S", "dont know",
                new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000), 150, 25, "image");

        loginManager = LoginManager.getInstance();
        try {
            companyFacade=(CompanyFacade)loginManager.login("sam@sung.com", "s2m5un6", ClientType.COMPANY );
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test
    public void loginPass() {
        try {
            Assert.assertTrue(loginManager.login("sam@sung.com", "s2m5un6", ClientType.COMPANY)instanceof CompanyFacade);
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
    }

    @Test(expected = CustomExceptions.class)
    public void loginFail() throws CustomExceptions {
            Assert.assertTrue(loginManager.login("shachar@yaks.com", "pjj123", ClientType.CUSTOMER)instanceof CompanyFacade);
    }

    @Test
    public void addCoupon() {
        companyFacade.addCoupon(coupon);
        companyFacade.addCoupon(new Coupon(0, 3, Category.PERSONAL_CARE, "soapy", "soap",
                new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis() + 60 * 1000), 3, 15, "image"));
    }

    @Test
    public void addCouponException(){
        companyFacade.addCoupon(coupon);
    }

    @Test
    public void setCoupon() {
        try {
            companyFacade.updateCoupon(coupon);
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
        System.out.println(companyFacade.getCompanyCoupons(Category.FOOD));
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
