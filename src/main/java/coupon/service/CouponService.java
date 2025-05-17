package coupon.service;

import coupon.model.Coupon;

public interface CouponService {
    boolean buyCoupon(String username, Coupon coupon); // 구매 시도
}
