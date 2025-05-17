package coupon.ui;

import coupon.controller.CouponController;
import coupon.model.Coupon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * [FrameCouponStore]
 * - 사용자가 포인트로 쿠폰을 구매할 수 있는 상점 화면
 */
public class FrameCouponStore extends JFrame {

    private final CouponController couponController;
    private final String username;

    public FrameCouponStore(CouponController couponController, String username) {
        this.couponController = couponController;
        this.username = username;

        setTitle("포인트 상점");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initializeUI();
    }

    private void initializeUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 10, 10));

        List<Coupon> coupons = couponController.getAvailableCoupons();
        for (Coupon coupon : coupons) {
            JButton button = new JButton(coupon.getName() + " - " + coupon.getPrice() + "P");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handlePurchase(coupon);
                }
            });
            panel.add(button);
        }

        add(panel);
    }

    private void handlePurchase(Coupon coupon) {
        boolean success = couponController.purchase(username, coupon);
        if (success) {
            JOptionPane.showMessageDialog(this,
                    coupon.getName() + " 쿠폰을 구매했습니다!",
                    "구매 성공",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "포인트가 부족합니다.",
                    "구매 실패",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
