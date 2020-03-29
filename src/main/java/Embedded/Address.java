package Embedded;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {
    @Column(name = "u_city")
    private String city;
    private String zipcode;
    private String street;

    public Address() {
    }

    public Address(String city, String s, String street) {
        this.city=city;
        this.zipcode=s;
        this.street=street;
    }

    public String getCity() {
        return city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getStreet() {
        return street;
    }
}
