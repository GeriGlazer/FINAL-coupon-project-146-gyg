package clients.beans;

import clients.exceptions.CustomExceptions;
import clients.exceptions.EnumExceptions;

import java.sql.Date;

/**
 * @author Yoav Hacmon, Guy Endvelt and Gery Glazer
 * 03.2022
 */

/**
 * define entity basic information
 */
public class Coupon {
    private int id;
    private int companyId;
    private Category category;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int amount;
    private double price;
    private String image;

    /**
     * default C`tor
     */
    public Coupon() {
    }

    /**
     *Full constructor
     * @param id coupon identifier
     * @param companyId refers to coupons company ownership
     * @param category refers to coupons category type
     * @param title coupons name
     * @param description message about coupon discount
     * @param startDate coupon start date
     * @param endDate coupon end date
     * @param amount defines number of coupons available for purchase
     * @param price coupons purchase value
     * @param image visual of coupon description
     */
    public Coupon(int id, int companyId, Category category, String title, String description, Date startDate, Date endDate, int amount, double price, String image) {
        this.id=id;
        this.companyId = companyId;
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate=startDate;
        this.endDate=endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) throws CustomExceptions {
        throw new CustomExceptions(EnumExceptions.CAN_NOT_CHANGE_THIS_VARIABLE);
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) throws CustomExceptions {
        throw new CustomExceptions(EnumExceptions.CAN_NOT_CHANGE_THIS_VARIABLE);
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(int whenToStart) {
        this.startDate = new Date(System.currentTimeMillis()+ 24L *60*60*1000*whenToStart);
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(int hawManyDays) {
        this.endDate = new Date(System.currentTimeMillis()+ 24L *60*60*1000*hawManyDays);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", amount=" + amount +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }
}
