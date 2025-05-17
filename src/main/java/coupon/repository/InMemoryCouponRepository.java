package coupon.repository;

import coupon.model.Coupon;
import coupon.model.CouponPurchase;

import java.time.LocalDate;
import java.util.*;

public class InMemoryCouponRepository implements CouponRepository {

    private final List<Coupon> couponList = new ArrayList<>();
    private final Map<String, List<CouponPurchase>> purchaseHistory = new HashMap<>();

    public InMemoryCouponRepository() {
        // 초기 더미 쿠폰 데이터
        couponList.add(new Coupon("C001", "비타500", 500));
        couponList.add(new Coupon("C002", "콜라", 700));
        couponList.add(new Coupon("C003", "커피", 1000));
    }

    @Override
    public List<Coupon> findAllAvailable() {
        return new ArrayList<>(couponList);
    }

    @Override
    public void savePurchase(String username, Coupon coupon) {
        purchaseHistory
                .computeIfAbsent(username, k -> new ArrayList<>())
                .add(new CouponPurchase(coupon, LocalDate.now()));
    }

    @Override
    public List<CouponPurchase> findPurchaseHistoryByUsername(String username) {
        return purchaseHistory.getOrDefault(username, new ArrayList<>());
    }
}
