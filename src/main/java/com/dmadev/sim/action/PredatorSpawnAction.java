package com.dmadev.sim.action;

import com.dmadev.sim.constants.Constants;
import com.dmadev.sim.creature.Predator;
import com.dmadev.sim.gameMap.Coordinates;


public  class PredatorSpawnAction  extends SpawnAction<Predator> {

    public PredatorSpawnAction(){
        super.countTypeOnMap= Constants.PREDATOR_SPAWN_COUNT;
    }


   protected Predator  spawnEntity(Coordinates coordinates){
       return new Predator(coordinates,Constants.PREDATOR_POWER);
   };
}
