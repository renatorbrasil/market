package com.eventsourcing.market.domain.base;

import lombok.Getter;

import java.util.UUID;

public class Entity {

    @Getter
    protected UUID id;

    public Entity(UUID id) {
        this.id = id;
    }

    public Entity() {
        this.id = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Entity) &&
                ((Entity) obj).getId().equals(id);
    }
}
