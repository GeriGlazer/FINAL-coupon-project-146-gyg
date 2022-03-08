package clients.facade;

import clients.exceptions.CustomExceptions;
import clients.dbDao.CompaniesDBDAO;
import clients.dbDao.CouponsDBDAO;
import clients.dbDao.CustomersDBDAO;
/**
 * @author Yoav Hachmon, Guy Endvelt and Gery Glazer
 * 03.2022
 */

/**
 * implemented by all facade classes
 * allows access to DBDAO methods
 */
public abstract class ClientFacade {

    protected CouponsDBDAO couponsDBDAO;
    protected CompaniesDBDAO companiesDBDAO;
    protected CustomersDBDAO customersDBDAO;

    public abstract boolean login(String email, String password) throws CustomExceptions;

}