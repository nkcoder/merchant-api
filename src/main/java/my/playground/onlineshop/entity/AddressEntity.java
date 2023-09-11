package my.playground.onlineshop.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("addresses")
@Getter
@RequiredArgsConstructor
public class AddressEntity {
    @Id
    @Setter
    private Long addressId;
    private final Long userId;
    private final String street;
    private final String city;
    private final String state;
    private final String zipCode;
    private final String country;
}
