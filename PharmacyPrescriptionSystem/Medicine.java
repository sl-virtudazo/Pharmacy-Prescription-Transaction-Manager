package PharmacyPrescriptionSystem;

public class Medicine {
    private String genericName;
    private String brandName;
    private int stock;
    private String expirationDate;
    private String manufacturer;
    private double price;

    public Medicine(String genericName, String brandName, int stock, String expirationDate, String manufacturer, double price) {
        this.genericName = genericName;
        this.brandName = brandName;
        this.stock = stock;
        this.expirationDate = expirationDate;
        this.manufacturer = manufacturer;
        this.price = price;
    }

    public String getGenericName() {
        return genericName;
    }
    public String getBrandName() {
        return brandName;
    }
    public int getStock() {
        return stock;
    }
    public String getExpirationDate() {
        return expirationDate;
    }
    public double getPrice() {
        return price;
    }
    public void setStock(int newStock) {
        this.stock = newStock;
    }
    public String getManufacturer() {
        return manufacturer;
    }
    public String getName() {
        return genericName;
    }
}