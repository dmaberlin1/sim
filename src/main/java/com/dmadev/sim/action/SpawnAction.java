package com.dmadev.sim.action;

import com.dmadev.sim.base.Entity;
import com.dmadev.sim.map.Coordinates;
import com.dmadev.sim.map.GameMap;

import java.util.stream.IntStream;


public abstract class SpawnAction<T extends Entity>  extends Action{
    protected int countTypeOnMap;

    @Override
    public void perform(GameMap gameMap){
        IntStream.range(0,countTypeOnMap).forEach
         (i ->  spawnEntityAtRandomEmptyPlace(gameMap));
    }


    private void spawnEntityAtRandomEmptyPlace(GameMap gameMap) {
        Coordinates coordinates = gameMap.getEmptyPlaceRandom();
        gameMap.setEntity(coordinates,spawnEntity(coordinates));
    }

    protected abstract T spawnEntity(Coordinates coordinates);
}
