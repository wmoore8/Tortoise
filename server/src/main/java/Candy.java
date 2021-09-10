public class Candy {

    private String name;
    private double stock;
    private double capacity;
    private double id;
    private double restockAmount = 0;

    public Candy(String name, double stock, double capacity, double id) {
        this.name = name;
        this.stock = stock;
        this.capacity = capacity;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public double getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
