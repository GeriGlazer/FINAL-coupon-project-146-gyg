package clients.facade;

import clients.CustomExceptions;
import clients.EnumExceptions;
import clients.beans.Category;
import clients.beans.Company;
import clients.beans.Coupon;
import clients.dbDao.CouponsDBDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CompanyFacade extends ClientFacade  {

private int companyId;
private CouponsDBDAO couponsDBDAO;

public CompanyFacade(int companyId) {
        this.companyId = companyId;
    }

    @Override
public boolean login(String email, String password) {
        String pas = null;
        String mail = null;
        try {
            pas = companiesDBDAO.getOneCompany(companyId).getPassword();
            mail = companiesDBDAO.getOneCompany(companyId).getEmail();
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());;
        }
        return email.equals(mail) && password.equals(pas);
    }

public void addCoupon(Coupon coupon){
    List<Coupon>couponList = companiesDBDAO.getCompanyCoupons(companyId).stream()
            .filter(item->item.getTitle().equals(coupon.getTitle())).collect(Collectors.toList());
    if(couponList.isEmpty()) {
        try {
            couponsDBDAO.addCoupon(coupon);
        } catch (CustomExceptions e) {
            System.out.println(EnumExceptions.COUPON_TITLE_EXIST);
        }
    }
}

public void updateCoupon(Coupon coupon){
    try {
        couponsDBDAO.updateCoupon(coupon);
    } catch (CustomExceptions e) {
        System.out.println(e.getMessage());
    }
}

public void deleteCoupon(int couponId){
    try {
        couponsDBDAO.deleteCoupon(couponId);
    }catch (CustomExceptions e){
        System.out.println(e.getMessage());
    }
}

public List<Coupon>getCompanyCoupons() {
    try {
        return couponsDBDAO.getCouponsByCompanyId(this.companyId);
    } catch (CustomExceptions e) {
        System.out.println(e.getMessage());
        return null;
    }
}

public List<Coupon>getCompanyCoupons(Category category){
        return  getCompanyCoupons().stream()
            .filter(item->item.getCategory().equals(category))
            .collect(Collectors.toList());
    }

public List<Coupon>getCompanyCoupons(double maxPrice){
        return getCompanyCoupons().stream()
        .filter(item->item.getPrice()<=maxPrice).collect(Collectors.toList());
}


public Company getCompanyDetails() {
    try {
        return companiesDBDAO.getOneCompany(companyId);
    } catch (CustomExceptions e) {
        System.out.println(e.getMessage());
        return null;
    }
}
}
