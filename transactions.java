public class transactions {
    public int Tcode, quantity;
    public String goods;
    public double totalPrice;

    public transactions() {

    }

    public transactions(int Tcode, int quantity, String goods, double Tprice) {
        this.Tcode = Tcode;
        this.quantity = quantity;
        this.goods = goods;
        this.totalPrice = Tprice;
    }

    public int getTcode() {
        return Tcode;
    }
    
    public int getQuantity() {
        return quantity;
    }

    public String getGoods() {
        return goods;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}