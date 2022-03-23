package clients.beans;

import clients.exceptions.CustomExceptions;
import clients.exceptions.EnumExceptions;
import clients.dbDao.CouponsDBDAO;

import java.util.List;

/**
 * @author Yoav Hacmon, Guy Endvelt and Gery Glazer
 * 03.2022
 */

/**
 * define entity basic information
 */
public class Company {
    private int id;
    private String name;
    private String email;
    private String password;
    private List<Coupon> coupons;
    CouponsDBDAO couponsDBDAO;

    /**
     * default C`tor
     */
    public Company(){
    }

    /**
     * Full constructor
     * @param id company identifier
     * @param name company name
     * @param email company e-mail address
     * @param password company password
     * @param coupons list of company's coupons
     */
    public Company(int id, String name, String email, String password, List<Coupon> coupons) {
        this.id = id;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) throws CustomExceptions{
        throw new CustomExceptions(EnumExceptions.CAN_NOT_CHANGE_NAME);
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
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", coupons=" + coupons +
                '}';
    }
}
