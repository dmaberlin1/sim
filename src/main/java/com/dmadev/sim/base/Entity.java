package com.dmadev.sim.base;

import com.dmadev.sim.constants.EntityType;
import com.dmadev.sim.gameMap.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
public abstract class Entity {

    @Getter
    private final EntityType type;
    public Coordinates coordinates;

}
