public class Address {
    private String street;
    private String streetNum;
    private String city;
    private String country;

    public Address(String street, String streetNum, String city, String country) {
        setStreet(street);
        setStreetNum(streetNum);
        setCity(city);
        setCountry(country);
    }

    // Getters
    public String getStreet() {return street;}
    public String getStreetNum() {return streetNum;}
    public String getCity() {return city;}
    public String getCountry() {return country;}
    public String getFullAddress() {
        return street + " " + streetNum + ", " + city + ", " + country;
    }

    // Setters
    public void setStreet(String street) {this.street = street;}
    public void setStreetNum(String streetNum) {this.streetNum = streetNum;}
    public void setCity(String city) {this.city = city;}
    public void setCountry(String country) {this.country = country;}

}
