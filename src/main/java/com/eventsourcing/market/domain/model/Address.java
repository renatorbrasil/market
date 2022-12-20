package com.eventsourcing.market.domain.model;

import com.eventsourcing.market.domain.exception.InvalidAddressInfoException;
import org.springframework.util.StringUtils;

public class Address {
    private String streetName;

    public Address(String streetName) {
        if (!StringUtils.hasText(streetName)) {
            throw new InvalidAddressInfoException();
        }
        this.streetName = streetName;
    }

    @Override
    public String toString() {
        return "Address: " + streetName;
    }
}
