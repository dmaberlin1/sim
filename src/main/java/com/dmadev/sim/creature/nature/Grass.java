package com.dmadev.sim.creature.nature;

import com.dmadev.sim.base.Entity;
import com.dmadev.sim.constants.EntityType;
import com.dmadev.sim.gameMap.Coordinates;

public class Grass extends Entity {
    public Grass( Coordinates coordinates) {
        super(EntityType.GRASS, coordinates);
    }
}
