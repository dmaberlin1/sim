package com.dmadev.sim.creature.nature;

import com.dmadev.sim.base.Entity;
import com.dmadev.sim.constants.EntityType;
import com.dmadev.sim.gameMap.Coordinates;

public class Rock extends Entity {
    public Rock(Coordinates coordinates) {
        super(EntityType.ROCK,coordinates);
    }
}
