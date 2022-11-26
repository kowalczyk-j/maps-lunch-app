public class Dish {
    private double price;
    private String name;
    private String description;
    private boolean isVegetarian;
    private boolean isVegan;

    public Dish(double price, String name, String description, boolean isVegetarian, boolean isVegan) {
        setPrice(price);
        setName(name);
        setDescription(description);
        setIsVegetarian(isVegetarian);
        setIsVegan(isVegan);
    }

    // Getters
    public double getPrice() {return price;}
    public String getName() {return name;}
    public String getDescription() {return description;}
    public boolean getIsVegetarian() {return isVegetarian;}
    public boolean getIsVegan(){return isVegan;}

    // Setters
    public void setPrice(double price) {this.price = price;}
    public void setName(String name) {this.name = name;}
    public void setDescription(String description) {this.description = description;}
    public void setIsVegetarian(boolean isVegetarian) {this.isVegetarian = isVegetarian;}
    public void setIsVegan(boolean isVegan){this.isVegan = isVegan;}

}
