package coupon.model;

import java.time.LocalDate;

public class CouponPurchase {
    private final Coupon coupon;
    private final LocalDate purchaseDate;

    public CouponPurchase(Coupon coupon, LocalDate purchaseDate) {
        this.coupon = coupon;
        this.purchaseDate = purchaseDate;
    }

    public Coupon getCoupon() { return coupon; }
    public LocalDate getPurchaseDate() { return purchaseDate; }
}

