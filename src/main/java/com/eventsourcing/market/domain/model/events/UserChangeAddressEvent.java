package com.eventsourcing.market.domain.model.events;

import com.eventsourcing.market.config.mongo.DocumentType;
import com.eventsourcing.market.domain.base.DomainEvent;
import com.eventsourcing.market.domain.model.Address;
import lombok.Getter;

import java.util.UUID;

@DocumentType("User.AddressChanged")
public class UserChangeAddressEvent extends DomainEvent {

    public UserChangeAddressEvent(UUID aggregateId, Address address) {
        super(aggregateId);
        this.address = address;
    }

    @Getter
    private Address address;
}
