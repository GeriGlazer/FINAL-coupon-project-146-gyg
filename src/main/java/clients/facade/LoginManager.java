package clients.facade;

import clients.beans.Company;
import clients.beans.Customer;
import clients.dbDao.CompaniesDBDAO;
import clients.dbDao.CouponsDBDAO;
import clients.dbDao.CustomersDBDAO;
import clients.exceptions.CustomExceptions;
import clients.exceptions.EnumExceptions;

import java.util.Objects;
import java.util.stream.Collectors;
/**
 * @author Yoav Hacmon, Guy Endvelt and Gery Glazer
 * 03.2022
 */

/**
 * Class responsible for retrieving to the client the right facade
 */
public class LoginManager {
    private static LoginManager instance = null;
    private static AdminFacade adminFacade = new AdminFacade();
    private static CustomerFacade customerFacade = new CustomerFacade();
    private static CompanyFacade companyFacade = new CompanyFacade();

    /**
     * Empty SINGLETON constructor
     * private, internal use only
     */
    private LoginManager() {
    }

    /**
     * create one instance of LoginManager class only
     * check to prevent the creation of two instances of the same class
     *
     * @return loginManager instance
     */
    public static LoginManager getInstance() {
        if (instance == null) {
                    instance = new LoginManager();
            }
        return instance;
        }

    /**
     *login administrator, opens different facades depending on client type
     * @param email input by user
     * @param password input by user
     * @param clientType input by user
     * @return facade
     */
    public ClientFacade login(String email, String password, ClientType clientType) {
        switch (clientType) {
            case ADMINISTRATOR:
                try{
                if (adminFacade.login(email, password)) {
                    System.out.println("admin connected");
                    return adminFacade;
                }} catch(CustomExceptions customExceptions){
                    System.out.println(customExceptions.getMessage());
                    return null;
                }
            case COMPANY:
                try{
                if (companyFacade.login(email, password)) {
                    System.out.println("company number " + companyFacade.getCompanyId() + " is connected");
                    return companyFacade;
                }} catch(CustomExceptions customExceptions) {
                    System.out.println(customExceptions.getMessage());
                    return null;
                }
            case CUSTOMER:
                try{
                if (customerFacade.login(email, password)) {
                    System.out.println("customer number " + customerFacade.getCustomerID() + " is connected");
                    return customerFacade;
                }}catch (CustomExceptions customExceptions){
                    System.out.println(customExceptions.getMessage());
                    return null;
                }
            default:
                System.out.println("wrong client type");
                return null;
        }
    }
}
