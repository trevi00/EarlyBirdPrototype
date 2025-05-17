package coupon.repository;

import coupon.model.Coupon;
import coupon.model.CouponPurchase;

import java.util.List;

public interface CouponRepository {
    List<Coupon> findAllAvailable();
    void savePurchase(String username, Coupon coupon);

    // ✅ 추가된 메서드
    List<CouponPurchase> findPurchaseHistoryByUsername(String username);
}
