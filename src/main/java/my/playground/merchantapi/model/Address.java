package my.playground.merchantapi.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("addresses")
@Getter
public class Address {
    @Id
    private final Long addressID;
    private final Long userID;
    private final String street;
    private final String city;
    private final String state;
    private final String zipCode;
    private final String country;

    public Address(Long addressID, Long userID, String street, String city, String state, String zipCode, String country) {
        this.addressID = addressID;
        this.userID = userID;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
    }
}
