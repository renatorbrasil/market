package com.eventsourcing.market.domain.model.user;

import com.eventsourcing.market.domain.events.DomainEvent;
import com.eventsourcing.market.domain.events.UserChangeAddressEvent;
import com.eventsourcing.market.domain.events.UserCreatedEvent;
import com.eventsourcing.market.domain.exception.EventNotSupportedException;
import com.eventsourcing.market.domain.model.EventSourcedAggregate;
import com.eventsourcing.market.domain.snapshot.UserSnapshot;
import lombok.Getter;

import java.util.UUID;

public class User extends EventSourcedAggregate {

    @Getter
    private Address address;

    public User(Address address) {
        super();
        causes(new UserCreatedEvent(getId(), address));
    }

    public User(UserSnapshot snapshot) {
        super(snapshot.getAggregateId(), snapshot.getEventNumber());
        this.address = snapshot.getAddress();
    }

    public UserSnapshot getSnapshot() {
        return new UserSnapshot(getId(), address, version);
    }

    public User(UUID id) {
        super(id);
    }

    public void changeAddress(Address address) {
        causes(new UserChangeAddressEvent(getId(), address));
    }

    @Override
    protected void applyEvent(DomainEvent change) {
        if (change instanceof UserCreatedEvent) {
            when((UserCreatedEvent) change);
        } else if (change instanceof UserChangeAddressEvent) {
            when((UserChangeAddressEvent) change);
        } else {
            throw new EventNotSupportedException();
        }
    }

    private void when(UserChangeAddressEvent event) {
        address = event.getAddress();
    }

    private void when(UserCreatedEvent event) {
        address = event.getAddress();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", version=" + version +
                ", address=" + address +
                '}';
    }
}
