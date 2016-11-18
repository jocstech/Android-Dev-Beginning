/**
 * Coded By Yulong Fang #100471536
 */

package csci4100.uoit.ca.a2pricebrowsing;


public class Product {

    // Product information:
    private int productId;
    private String name;
    private String description;
    private float price;

    public Product() {

        // Initializing:
        this.productId=-1;
        this.name=null;
        this.description=null;
        this.price=-1;

    }

    public Product(int productId , String name , String description , float price) {

        //Setting up:
        this.productId=productId;
        this.name=name;
        this.description=description;
        this.price=price;
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "ID:"+this.productId+" Name:"+this.name+" Description:"+this.description+" Price:"+this.price;
    }
}
