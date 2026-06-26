
public class items {

    public int stock;
    public String code;
    public double price;
    public String name;

    public items(String code, String name, double price, int stock) {
        this.stock = stock;
        this.code = code;
        this.price = price;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

}