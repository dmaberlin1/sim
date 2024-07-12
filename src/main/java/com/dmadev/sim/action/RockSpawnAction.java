package com.dmadev.sim.action;

import com.dmadev.sim.base.Constants;
import com.dmadev.sim.base.Entity;
import com.dmadev.sim.map.Coordinates;
import com.dmadev.sim.map.GameMap;
import com.dmadev.sim.nature.Rock;


public  class RockSpawnAction  extends SpawnAction<Rock>{
    public RockSpawnAction(){
        super.countTypeOnMap= Constants.ROCK_SPAWN_COUNT;
    }
   protected Rock spawnEntity(Coordinates coordinates){
        return new Rock(coordinates);
   };
}
