package com.dmadev.sim.creature;

import com.dmadev.sim.base.Creature;
import com.dmadev.sim.constants.EntityType;
import com.dmadev.sim.gameMap.Coordinates;


public final class Herbivore extends Creature {
    public Herbivore(Coordinates coordinates) {
        super(EntityType.HERBIVORE, coordinates);
    }

}
