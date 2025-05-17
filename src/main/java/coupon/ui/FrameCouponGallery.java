package coupon.ui;

import coupon.controller.CouponController;
import coupon.model.CouponPurchase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * [FrameCouponGallery]
 * - 유저가 구매한 쿠폰 내역을 보여주는 보관함 화면
 */
public class FrameCouponGallery extends JFrame {

    private final CouponController couponController;
    private final String username;

    public FrameCouponGallery(CouponController couponController, String username) {
        this.couponController = couponController;
        this.username = username;

        setTitle("쿠폰 보관함");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        List<CouponPurchase> history = couponController.getPurchaseHistory(username);

        String[] columnNames = {"쿠폰 이름", "가격", "구매일"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (CouponPurchase purchase : history) {
            Object[] row = {
                    purchase.getCoupon().getName(),
                    purchase.getCoupon().getPrice(),
                    purchase.getPurchaseDate().toString()
            };
            model.addRow(row);
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);
    }
}
