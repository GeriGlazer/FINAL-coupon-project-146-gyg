package clients.beans;

import clients.exceptions.CustomExceptions;
import clients.exceptions.EnumExceptions;

import java.util.List;

/**
 * @author Yoav Hacmon, Guy Endvelt and Gery Glazer
 * 03.2022
 */

/**
 * define entity basic information
 */
public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Coupon> coupons;

    /**
     * default C`tor
     */
    public Customer() {
    }

    /**
     * Full constructor
     * @param id customer identifier
     * @param firstName customer first name
     * @param lastName customer last name
     * @param email customer e-mail address
     * @param password customer password
     * @param coupons list of customer coupons
     */
    public Customer(int id, String firstName, String lastName, String email, String password, List<Coupon> coupons) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.coupons = coupons;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) throws CustomExceptions {
        throw new CustomExceptions(EnumExceptions.CAN_NOT_CHANGE_THIS_VARIABLE);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", coupons=" + coupons +
                '}';
    }
}
