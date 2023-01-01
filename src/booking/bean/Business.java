package booking.bean;

public class Business extends User{
    //store name
    private String ShopName;
    //address
    private String address;
    
    public String getShopName() {
        return ShopName;
    }
    public void setShopName(String shopName) {
        ShopName = shopName;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

}
