package com.dmadev.sim.nature;

import com.dmadev.sim.base.Entity;
import com.dmadev.sim.base.EntityType;
import com.dmadev.sim.map.Coordinates;
import org.springframework.stereotype.Component;

public class Grass extends Entity {
    public Grass( Coordinates coordinates) {
        super(EntityType.GRASS, coordinates);
    }
}
