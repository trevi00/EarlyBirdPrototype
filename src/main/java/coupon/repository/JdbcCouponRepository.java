package coupon.repository;

import coupon.model.Coupon;
import coupon.model.CouponPurchase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcCouponRepository implements CouponRepository {

    private final Connection conn;

    public JdbcCouponRepository(Connection conn) {
        this.conn = conn;
    }

    /**
     * 판매 중인 모든 쿠폰을 조회
     */
    @Override
    public List<Coupon> findAllAvailable() {
        List<Coupon> list = new ArrayList<>();
        String sql = "SELECT ID, NAME, PRICE FROM COUPONS";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new Coupon(
                        rs.getString("ID"),
                        rs.getString("NAME"),
                        rs.getInt("PRICE")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 유저가 쿠폰을 구매한 내역을 저장
     */
    @Override
    public void savePurchase(String username, Coupon coupon) {
        String sql = "INSERT INTO COUPON_HISTORY (USERNAME, COUPON_ID) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, coupon.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 특정 유저의 구매 내역을 조회
     */
    @Override
    public List<CouponPurchase> findPurchaseHistoryByUsername(String username) {
        List<CouponPurchase> list = new ArrayList<>();
        String sql = "SELECT c.ID, c.NAME, c.PRICE, h.PURCHASE_DATE " +
                "FROM COUPON_HISTORY h JOIN COUPONS c ON h.COUPON_ID = c.ID " +
                "WHERE h.USERNAME = ? ORDER BY h.PURCHASE_DATE DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Coupon coupon = new Coupon(
                            rs.getString("ID"),
                            rs.getString("NAME"),
                            rs.getInt("PRICE")
                    );
                    Date date = rs.getDate("PURCHASE_DATE");
                    LocalDate localDate = date.toLocalDate();
                    list.add(new CouponPurchase(coupon, localDate));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
