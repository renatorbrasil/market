package com.eventsourcing.market.domain.events;

import com.eventsourcing.market.config.mongo.DocumentType;
import com.eventsourcing.market.domain.model.user.Address;
import lombok.Getter;

import java.util.UUID;

@DocumentType("User.AddressChanged")
public class UserChangeAddressEvent extends DomainEvent {

    public UserChangeAddressEvent(UUID aggregateId, Address address) {
        super(aggregateId);
        this.address = address;
    }

    @Getter
    private final Address address;
}
