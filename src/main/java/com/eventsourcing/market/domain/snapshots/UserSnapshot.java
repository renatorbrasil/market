package com.eventsourcing.market.domain.snapshots;

import com.eventsourcing.market.domain.model.user.Account;
import com.eventsourcing.market.domain.model.user.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UserSnapshot {
    private UUID id;
    private Account account;
    private Address address;
}
