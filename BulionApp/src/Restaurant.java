public class Restaurant {
    private String name;
    private Address address;
    private Menu lunchMenu;
    private double rating;

    public Restaurant(String name, Address address, double rating,
                      Menu lunchMenu) {
        setName(name);
        setAddress(address);
        setRating(rating);
        setLunchMenu(lunchMenu);
    }

    // Getters
    public String getName() {return name;}
    public Address getAddress() {return address;}
    public double getRating() {return rating;}
    public Menu getLunchMenu() {return lunchMenu;}

    // Setters
    public void setName(String name) {this.name = name;}
    public void setAddress(Address address) {this.address = address;}
    public void setRating(double rating) {this.rating = rating;}
    public void setLunchMenu(Menu lunchMenu) {this.lunchMenu = lunchMenu;}

}
