package coupon.controller;

import coupon.model.Coupon;
import coupon.model.CouponPurchase;
import coupon.repository.CouponRepository;
import coupon.service.CouponService;

import java.util.List;

public class CouponController {
    private final CouponService couponService;
    private final CouponRepository couponRepository;

    public CouponController(CouponService service, CouponRepository repo) {
        this.couponService = service;
        this.couponRepository = repo;
    }

    public List<Coupon> getAvailableCoupons() {
        return couponRepository.findAllAvailable();
    }

    public boolean purchase(String username, Coupon coupon) {
        return couponService.buyCoupon(username, coupon);
    }

    // ✅ 추가된 메서드
    public List<CouponPurchase> getPurchaseHistory(String username) {
        return couponRepository.findPurchaseHistoryByUsername(username);
    }
}
