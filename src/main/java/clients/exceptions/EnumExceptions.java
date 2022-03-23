package clients.exceptions;
/**
 * @author Yoav Hacmon, Guy Endvelt and Gery Glazer
 *  * 03.2022
 */

/**
 * keys to exceptions
 */
public enum EnumExceptions {
    /**
     * General exceptions
     */
    NAME_EXIST("Cannot add existing name."),
    EMAIL_EXIST("Cannot add existing email."),
    ID_NOT_EXIST("ID not exist in database."),
    CAN_NOT_CHANGE_NAME("Can't change company/customer/coupon name"),
    CAN_NOT_CHANGE_THIS_VARIABLE("Can't change company/customer/coupon id"),
    /**
     * coupons exceptions
     */
    COUPON_ALREADY_EXIST("Cannot add coupon, coupon already exist."),
    COUPON_TITLE_EXIST("Cannot add coupon, title already exist."),
    COUPON_PURCHASED("Cannot purchase this item, already in your list."),
    NO_COUPONS("No coupons in database."),
    COUPONS_OUT_OF_STOCK("Coupons out of stock."),
    NO_COUPONS_BY_CATEGORY("No coupons in this category."),
    NO_COUPONS_BY_PRICE("No coupons under this price."),
    /**
     * company exceptions
     */
    NO_COMPANY("No company with this ID in database."),
    COMPANY_DOES_NOT_HAVE_THIS_COUPON("Company doesn't have this coupon."),
    INVALID_PERMITION("you are not allowed to do this"),

    /**
     * customer exceptions
     */
    NO_CUSTOMER("No customer with this ID in database."),
    /**
     * category exceptions
     */
    CATEGORY_EXIST("Category already exist in database."),
    CATEGORY_NOT_EXIST("Category doesn't exist in database."),
    /**
     * login exceptions
     */
    NOT_ADMIN("You are not Administrator Kapara."),
    FAIL_2_CONNECT("Fail to connect, please check entered email and password for error.");

    private String message;

    private EnumExceptions(String message){
        setMessage(message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
