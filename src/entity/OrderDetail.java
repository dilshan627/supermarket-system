package entity;

public class OrderDetail {
    private String OrderID;
    private String ItemCode;
    private int Orderqty;
    private double Discount;
    private double Price;

    public OrderDetail() {
    }

    public OrderDetail(String orderID) {
        OrderID = orderID;
    }

    public OrderDetail(String orderID, String itemCode, int orderqty, double discount, double price) {
        OrderID = orderID;
        ItemCode = itemCode;
        Orderqty = orderqty;
        Discount = discount;
        Price = price;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public int getOrderqty() {
        return Orderqty;
    }

    public void setOrderqty(int orderqty) {
        Orderqty = orderqty;
    }

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double discount) {
        Discount = discount;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }
}
