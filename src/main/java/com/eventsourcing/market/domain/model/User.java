package com.eventsourcing.market.domain.model;

import com.eventsourcing.market.domain.base.DomainEvent;
import com.eventsourcing.market.domain.base.EventSourcedAggregate;
import com.eventsourcing.market.domain.exception.EventNotSupportedException;
import com.eventsourcing.market.domain.model.events.UserAccountDebitEvent;
import com.eventsourcing.market.domain.model.events.UserChangeAddressEvent;
import com.eventsourcing.market.domain.model.events.UserCreatedEvent;
import lombok.Getter;

import java.util.UUID;

public class User extends EventSourcedAggregate {

    @Getter
    private Account account;

    @Getter
    private Address address;

    public User(Address address, Account account) {
        super();
        causes(new UserCreatedEvent(getId(), account, address));
    }

    public User(UUID id) {
        super(id);
    }

    public void changeAddress(Address address) {
        causes(new UserChangeAddressEvent(getId(), address));
    }

    public void makePayment(Money amount) {
        causes(new UserAccountDebitEvent(getId(), amount));
    }

    @Override
    protected void applyEvent(DomainEvent change) {
        if (change instanceof UserAccountDebitEvent) {
            when((UserAccountDebitEvent) change);
        } else if (change instanceof UserChangeAddressEvent) {
            when((UserChangeAddressEvent) change);
        } else if (change instanceof UserCreatedEvent) {
            when((UserCreatedEvent) change);
        } else {
            throw new EventNotSupportedException();
        }
    }

    private void when(UserAccountDebitEvent event) {
        account = account.debit(event.getAmount());
    }

    private void when(UserChangeAddressEvent event) {
        address = event.getAddress();
    }

    private void when(UserCreatedEvent event) {
        account = event.getAccount();
        address = event.getAddress();
    }
}
