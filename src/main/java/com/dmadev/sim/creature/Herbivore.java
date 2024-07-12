package com.dmadev.sim.creature;

import com.dmadev.sim.base.Creature;
import com.dmadev.sim.base.EntityType;
import com.dmadev.sim.map.Coordinates;


public final class Herbivore extends Creature {
    public Herbivore(Coordinates coordinates) {
        super(EntityType.HERBIVORE, coordinates);
    }


    //Травоядное, наследуется от Creature.
    // Стремятся найти ресурс (траву),
    // может потратить свой ход на движение в сторону травы, либо на её поглощение.
}
