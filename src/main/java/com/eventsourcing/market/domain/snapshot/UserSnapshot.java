package com.eventsourcing.market.domain.snapshot;

import com.eventsourcing.market.config.mongo.DocumentType;
import com.eventsourcing.market.domain.model.user.Address;
import lombok.Getter;

import java.util.UUID;

@Getter
@DocumentType("Snapshot.User")
public class UserSnapshot extends Snapshot {

    private final Address address;

    public UserSnapshot(
            UUID aggregateId,
            Address address,
            Integer eventNumber
    ) {
        super(aggregateId, eventNumber);
        this.address = address;
    }
}
