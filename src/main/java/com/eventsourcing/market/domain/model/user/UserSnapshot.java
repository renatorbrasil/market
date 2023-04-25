package com.eventsourcing.market.domain.model.user;

import com.eventsourcing.market.domain.model.Snapshot;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UserSnapshot extends Snapshot {

    private Address address;

    public UserSnapshot(
            UUID aggregateId,
            Address address,
            Integer eventNumber
    ) {
        super(aggregateId, eventNumber);
        this.address = address;
    }
}
