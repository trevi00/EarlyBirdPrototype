package coupon.model;

public class Coupon {
    private String id;        // 쿠폰 ID (예: "COUPON001")
    private String name;      // 쿠폰 이름 (예: "비타500")
    private int price;        // 쿠폰 가격

    public Coupon(String id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getPrice() { return price; }
}
