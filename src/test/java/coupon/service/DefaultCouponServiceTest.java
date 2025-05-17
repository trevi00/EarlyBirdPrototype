package coupon.service;

import bird.model.Bird;
import bird.repository.InMemoryBirdRepository;
import bird.service.BirdService;
import bird.service.DefaultBirdService;
import bird.service.StageEvolutionPolicy;
import coupon.controller.CouponController;
import coupon.model.Coupon;
import coupon.model.CouponPurchase;
import coupon.repository.CouponRepository;
import coupon.repository.InMemoryCouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultCouponServiceTest {

    private CouponController couponController;
    private BirdService birdService;
    private CouponRepository couponRepository;

    private final String username = "test1";

    @BeforeEach
    public void setUp() {
        // 새 + 포인트 초기화
        InMemoryBirdRepository birdRepo = new InMemoryBirdRepository();
        Bird bird = new Bird(username);
        bird.addPoint(1000); // 초기 포인트 1000
        birdRepo.save(bird);

        birdService = new DefaultBirdService(new StageEvolutionPolicy(), birdRepo);

        // 쿠폰 저장소 + 서비스 구성
        couponRepository = new InMemoryCouponRepository();
        CouponService couponService = new DefaultCouponService(couponRepository, birdService);
        couponController = new CouponController(couponService, couponRepository);
    }

    @Test
    public void testBuyCoupon_success() {
        Coupon coupon = new Coupon("C001", "비타500", 500);

        boolean result = couponController.purchase(username, coupon);
        assertTrue(result, "포인트가 충분하므로 구매 성공해야 합니다.");

        // 구매 내역 확인
        List<CouponPurchase> history = couponController.getPurchaseHistory(username);
        assertEquals(1, history.size());
        assertEquals("비타500", history.get(0).getCoupon().getName());
    }

    @Test
    public void testBuyCoupon_fail_dueToInsufficientPoints() {
        Coupon expensive = new Coupon("C999", "고급 커피", 9999);

        boolean result = couponController.purchase(username, expensive);
        assertFalse(result, "포인트 부족으로 구매 실패해야 합니다.");

        List<CouponPurchase> history = couponController.getPurchaseHistory(username);
        assertTrue(history.isEmpty(), "구매 내역이 없어야 합니다.");
    }

    @Test
    public void testBuyCoupon_reducesBirdPoints() {
        Coupon coupon = new Coupon("C002", "콜라", 700);

        boolean result = couponController.purchase(username, coupon);
        assertTrue(result);

        int remainingPoint = birdService.getPoint(username);
        assertEquals(300, remainingPoint, "700P 쿠폰 구매 후 남은 포인트는 300이어야 함");
    }
}
