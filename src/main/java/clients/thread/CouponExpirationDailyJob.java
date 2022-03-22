package clients.thread;

import clients.exceptions.CustomExceptions;
import clients.exceptions.EnumExceptions;
import clients.beans.Coupon;
import clients.dbDao.CouponsDBDAO;
import java.time.LocalDate;
/**
 * @author Yoav Hacmon, Guy Endvelt and Gery Glazer
 * 03.2022
 */

/**
 * Daily task to check and remove expired coupons from database
 */
public class CouponExpirationDailyJob implements Runnable {
    private CouponsDBDAO myCoupons = new CouponsDBDAO();
    private boolean quit = false;

    public CouponExpirationDailyJob() {
    }

    @Override
    public void run() {
        while (!quit) {
            try {
                System.out.println("Daily job started");
                for (Coupon item : myCoupons.getAllCoupons()) {
                    if (item.getEndDate().toLocalDate().isAfter(LocalDate.now())) {
                        myCoupons.deleteCoupon(item.getId());
                    }
                }
                //Thread.sleep(1000 * 60 * 60 * 24);
                Thread.sleep(1000*60);
            } catch (InterruptedException | CustomExceptions e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void stop(){
        this.quit = true;
    }



}
