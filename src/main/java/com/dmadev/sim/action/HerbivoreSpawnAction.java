package com.dmadev.sim.action;

import com.dmadev.sim.constants.Constants;
import com.dmadev.sim.creature.Herbivore;
import com.dmadev.sim.gameMap.Coordinates;


public  class HerbivoreSpawnAction extends SpawnAction<Herbivore>{

    public HerbivoreSpawnAction(){
        super.countTypeOnMap= Constants.HERBIVORE_SPAWN_COUNT;
    }


   protected Herbivore spawnEntity(Coordinates coordinates){
        return new Herbivore(coordinates);
   }
}
