package com.dmadev.sim.action;

import com.dmadev.sim.base.Constants;
import com.dmadev.sim.map.Coordinates;
import com.dmadev.sim.nature.Grass;

public class GrassSpawnAction extends SpawnAction<Grass>{
    public GrassSpawnAction() {
        super.countTypeOnMap= Constants.GRASS_SPAWN_COUNT;
    }

    @Override
    protected Grass spawnEntity(Coordinates coordinates) {
        return  new Grass(coordinates);
    }
}
