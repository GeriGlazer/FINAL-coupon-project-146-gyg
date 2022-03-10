package clients;

import clients.beans.Company;
import clients.beans.Customer;
import clients.db.ConnectionPool;
import clients.exceptions.CustomExceptions;
import clients.facade.*;
import clients.thread.CouponExpirationDailyJob;

import java.util.ArrayList;

/**
 * @author Yoav Hachmon, Guy Endvelt and Gery Glazer
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
    private static Customer customer = new Customer(1, "Dana", "Sercovich", "dana@serco.com", "54321", new ArrayList<>());
    //todo: דף 20

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
        System system = new System();
        system = System.getInstance();
        loginManager = LoginManager.getInstance();
        //check login for all 3 clients type
        adminFacade = (AdminFacade) loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
        adminFacade.addCompany(company);
        adminFacade.addCustomer(customer);
        customerFacade = (CustomerFacade) loginManager.login(customer.getEmail(), customer.getPassword(), ClientType.CUSTOMER);
        companyFacade = (CompanyFacade) loginManager.login(company.getEmail(), company.getPassword(), ClientType.COMPANY);

        //compile all facade tests (GO TO test->java->FinalTests class)

        //end thread
        //close connection to database
        system.stopAll();
    }
}
