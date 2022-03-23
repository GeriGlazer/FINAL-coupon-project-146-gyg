package clients.thread;

import clients.exceptions.CustomExceptions;
import clients.exceptions.EnumExceptions;
import clients.beans.Coupon;
import clients.dbDao.CouponsDBDAO;

import java.sql.Date;
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
                    if(item.getEndDate().getTime()<System.currentTimeMillis()){
                        myCoupons.deleteCoupon(item.getId());;
                        System.out.println("deleting coupon id " + item.getId());
                    }
                }
                Thread.sleep(1000 * 60 * 60 * 24); //this is the real thread
                //Thread.sleep(1000*15); //this is the thread sleep time for testing
            } catch (InterruptedException | CustomExceptions e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void stop(){
        this.quit = true;
    }



}
