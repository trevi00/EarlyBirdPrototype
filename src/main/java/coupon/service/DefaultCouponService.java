package coupon.service;

import bird.service.BirdService;
import coupon.model.Coupon;
import coupon.repository.CouponRepository;

public class DefaultCouponService implements CouponService {
    private final CouponRepository couponRepository;
    private final BirdService birdService;

    public DefaultCouponService(CouponRepository repo, BirdService birdService) {
        this.couponRepository = repo;
        this.birdService = birdService;
    }

    @Override
    public boolean buyCoupon(String username, Coupon coupon) {
        int currentPoint = birdService.getPoint(username);
        if (currentPoint < coupon.getPrice()) {
            return false;
        }
        birdService.usePoint(username, coupon.getPrice());
        couponRepository.savePurchase(username, coupon);
        return true;
    }
}
