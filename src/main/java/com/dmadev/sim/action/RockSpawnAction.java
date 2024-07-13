package com.dmadev.sim.action;

import com.dmadev.sim.constants.Constants;
import com.dmadev.sim.gameMap.Coordinates;
import com.dmadev.sim.creature.nature.Rock;


public  class RockSpawnAction  extends SpawnAction<Rock>{
    public RockSpawnAction(){
        super.countTypeOnMap= Constants.ROCK_SPAWN_COUNT;
    }
   protected Rock spawnEntity(Coordinates coordinates){
        return new Rock(coordinates);
   };
}
