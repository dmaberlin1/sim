package com.dmadev.sim.nature;

import com.dmadev.sim.base.Entity;
import com.dmadev.sim.base.EntityType;
import com.dmadev.sim.map.Coordinates;

public class Rock extends Entity {
    public Rock(Coordinates coordinates) {
        super(EntityType.ROCK,coordinates);
    }
}
