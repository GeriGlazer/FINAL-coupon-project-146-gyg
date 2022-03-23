package clients;

import clients.beans.Category;
import clients.beans.Company;
import clients.beans.Coupon;
import clients.beans.Customer;
import clients.db.ConnectionPool;
import clients.db.DBManager;
import clients.db.DBTools;
import clients.exceptions.CustomExceptions;
import clients.facade.*;
import clients.thread.CouponExpirationDailyJob;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static clients.beans.CategoryClass.addCategory;

/**
 * @author Yoav Hacmon, Guy Endvelt and Gery Glazer
 * 03.2022
 */

/**
 * Program initialization-Test class
 */
public class System {
    private static System instance=null;
    CouponExpirationDailyJob job = null;
    private static LoginManager loginManager;
    private static CustomerFacade customerFacade;
    private static CompanyFacade companyFacade;
    private static AdminFacade adminFacade;
    private static Company company = new Company(1, "Shachar", "shachar@yaks.com", "pjj123", new ArrayList<>());
    private static Customer customer = new Customer(1, "Dan", "Serc", "dan@serc.com", "54321", new ArrayList<>());
    private static Coupon coupon = new Coupon(1, 1,Category.FOOD, "bibi's coupon'S", "dont know",
            new Date(java.lang.System.currentTimeMillis()),
            new Date(java.lang.System.currentTimeMillis()), 150, 25, "image");
    private static List<Category> categories;

    /**
     * singleton. constructor for thread init
     */
    private System () {
        job = new CouponExpirationDailyJob();
        Thread task = new Thread(job);
        task.start();//start thread
    }

    /**
     * get one single instance of program init
     * @return System Object
     */
    public static synchronized System getInstance(){//throws classNotFound/SQL/Interrupted
        if (instance == null) {
            instance = new System();
        }
        return instance;
    }

    /**
     * ends thread daily job and close connection to database
     */
    public void stopAll(){
        job.stop();
        ConnectionPool.getInstance().closeAllConnections();
    }

    /**
     * Program overall initializer
     */
    public static void testAll() throws CustomExceptions {
        //system init
        System system =  System.getInstance();
        loginManager = LoginManager.getInstance();
        //check login for all 3 clients type

        adminFacade = (AdminFacade) loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
        adminFacade.addCompany(company);
        adminFacade.addCustomer(customer);
        customerFacade = (CustomerFacade) loginManager.login(customer.getEmail(), customer.getPassword(), ClientType.CUSTOMER);
        companyFacade = (CompanyFacade) loginManager.login(company.getEmail(), company.getPassword(), ClientType.COMPANY);
        companyFacade.addCoupon(coupon);
        //end thread
        //close connection to database
        //system.stopAll();
    }

    /**
     *start over new clean sheet
     */
    public static void cleanDataBase(){
        DBTools.runQuery(DBManager.DROP_CUSTOMER_VS_COUPONS_TABLE);
        DBTools.runQuery(DBManager.DROP_COUPONS_TABLE);
        DBTools.runQuery(DBManager.DROP_COMPANY_TABLE);
        DBTools.runQuery(DBManager.DROP_CUSTOMERS_TABLE);

        DBTools.runQuery(DBManager.CREATE_COMPANY_TABLE);
        DBTools.runQuery(DBManager.CREATE_CUSTOMER_TABLE);
        DBTools.runQuery(DBManager.CREATE_COUPONS_TABLE);
        DBTools.runQuery(DBManager.CREATE_CUSTOMER_VS_COUPONS_TABLE);
    }

    /**
     * create databases for beginning
     */
    public static void createDataBases(){
        DBTools.runQuery(DBManager.CREATE_CATEGORIES_TABLE);
        DBTools.runQuery(DBManager.CREATE_COMPANY_TABLE);
        DBTools.runQuery(DBManager.CREATE_CUSTOMER_TABLE);
        DBTools.runQuery(DBManager.CREATE_COUPONS_TABLE);
        DBTools.runQuery(DBManager.CREATE_CUSTOMER_VS_COUPONS_TABLE);
        createCategories();
    }
    public static void createCategories(){
        categories= Arrays.stream(Category.values()).collect(Collectors.toList());
        for (Category item : categories) {
            try {
                addCategory(item);
            } catch (CustomExceptions customExceptions) {
                java.lang.System.out.println(customExceptions.getMessage());
            }
        }
    }
}
