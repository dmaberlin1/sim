package com.dmadev.sim.action;

import com.dmadev.sim.constants.Constants;
import com.dmadev.sim.gameMap.Coordinates;
import com.dmadev.sim.creature.nature.Grass;

public class GrassSpawnAction extends SpawnAction<Grass>{
    public GrassSpawnAction() {
        super.countTypeOnMap= Constants.GRASS_SPAWN_COUNT;
    }

    @Override
    protected Grass spawnEntity(Coordinates coordinates) {
        return  new Grass(coordinates);
    }
}
