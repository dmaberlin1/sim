package com.dmadev.sim.action;

import com.dmadev.sim.base.Constants;
import com.dmadev.sim.base.Entity;
import com.dmadev.sim.creature.Herbivore;
import com.dmadev.sim.map.Coordinates;
import com.dmadev.sim.map.GameMap;


public  class HerbivoreSpawnAction extends SpawnAction<Herbivore>{

    public HerbivoreSpawnAction(){
        super.countTypeOnMap= Constants.HERBIVORE_SPAWN_COUNT;
    }


   protected Herbivore spawnEntity(Coordinates coordinates){
        return new Herbivore(coordinates);
   }
}
