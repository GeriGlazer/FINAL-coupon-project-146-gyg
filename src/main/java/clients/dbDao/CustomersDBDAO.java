package clients.dbDao;

import clients.exceptions.CustomExceptions;
import clients.exceptions.EnumExceptions;
import clients.beans.Customer;
import clients.dao.CustomersDAO;
import clients.db.DBManager;
import clients.db.DBTools;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yoav Hacmon, Guy Endvelt and Gery Glazer
 * 03.2022
 */
public class CustomersDBDAO implements CustomersDAO {
    private CouponsDBDAO couponsDBDAO = new CouponsDBDAO();

    /**
     * Checks the existence of a customer in database by customer email and password
     *
     * @param email    input e-mail address
     * @param password input password
     * @return true or false
     */
    @Override
    public boolean isCustomerExist(String email, String password) {
        Map<Integer, Object> values = new HashMap<>();
        try {
            values.put(1, email);
            values.put(2, password);
            ResultSet resultSet = DBTools.runQueryForResult(DBManager.IS_CUSTOMER_EXISTS_BY_EMAIL_AND_PASS, values);
            assert resultSet != null;
            resultSet.next();
            return resultSet.getInt(1) == 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Checks the existence of a customer in database by customer email
     *
     * @param email input customer email
     * @return true or false
     */
    public boolean isCustomerExistsByMail(String email) {
        Map<Integer, Object> values = new HashMap<>();
        try {
            values.put(1, email);
            ResultSet resultSet = DBTools.runQueryForResult(DBManager.IS_CUSTOMER_EXISTS_BY_EMAIL, values);
            assert resultSet != null;
            resultSet.next();
            return (resultSet.getInt(1) == 1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * insert new customer info into database
     *
     * @param customer input customer object
     * @throws CustomExceptions alerts user if email already exist in database
     */
    @Override
    public void addCustomer(Customer customer) throws CustomExceptions {
        if (isCustomerExistsByMail(customer.getEmail())) {
            throw new CustomExceptions(EnumExceptions.EMAIL_EXIST);
        } else {
            Map<Integer, Object> values = new HashMap<>();
            values.put(1, customer.getFirstName());
            values.put(2, customer.getLastName());
            values.put(3, customer.getEmail());
            values.put(4, customer.getPassword());
            DBTools.runQuery(DBManager.ADD_CUSTOMER, values);
        }
    }

    /**
     * update customer info into data base
     *
     * @param customer input customer object
     * @throws CustomExceptions alerts user if customer id not found in database
     */
    @Override
    public void updateCustomer(Customer customer) throws CustomExceptions {
        if (getOneCustomer(customer.getId()) == null) {
            throw new CustomExceptions(EnumExceptions.ID_NOT_EXIST);
        }else {
            Map<Integer, Object> values = new HashMap<>();
            values.put(1, customer.getFirstName());
            values.put(2, customer.getLastName());
            values.put(3, customer.getEmail());
            values.put(4, customer.getPassword());
            values.put(5, customer.getId());
            DBTools.runQuery(DBManager.UPDATE_CUSTOMER, values);
        }
    }

    /**
     * removes customer from database
     *
     * @param customerID input customer id for removal
     * @throws CustomExceptions alerts user if customer id not found in database
     */
    @Override
    public void deleteCustomer(int customerID) throws CustomExceptions {
        if (getOneCustomer(customerID) == null) {
            throw new CustomExceptions(EnumExceptions.ID_NOT_EXIST);
        }else {
            Map<Integer, Object> values = new HashMap<>();
            values.put(1, customerID);
            DBTools.runQuery(DBManager.DELETE_CUSTOMER, values);
        }
    }

    /**
     * gets list of all customers in database
     *
     * @return list of customers
     */
    @Override
    public List<Customer> getAllCustomers() {
        ResultSet resultSet = DBTools.runQueryForResult(DBManager.GET_ALL_CUSTOMERS);
        List<Customer> customers = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Customer customer = getOneCustomer(resultSet.getInt("id"));
                customers.add(customer);
            }
        } catch (SQLException | CustomExceptions err) {
            System.out.println(err.getMessage());
        }
        return customers;
    }

    /**
     * retrieve one customer from database by ID
     *
     * @param customerID input customer ID
     * @return customer object info
     */
    @Override
    public Customer getOneCustomer(int customerID) throws CustomExceptions {
        Customer customer = null;
        couponsDBDAO = new CouponsDBDAO();
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, customerID);
        ResultSet resultSet = DBTools.runQueryForResult(DBManager.GET_ONE_CUSTOMER, values);

        try {
            if(!resultSet.next()){
                    throw new CustomExceptions(EnumExceptions.NO_CUSTOMER);
            }
            customer = new Customer(
                    resultSet.getInt("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    couponsDBDAO.getCouponsByCustomerId(customerID)
            );
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            return null;
        }
        return customer;
    }
}
