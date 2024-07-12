package com.dmadev.sim.action;

import com.dmadev.sim.base.Creature;
import com.dmadev.sim.map.Coordinates;
import com.dmadev.sim.map.GameMap;

public abstract class MoveAction extends Action {
    @Override
    public  void perform(GameMap gameMap){
        // Получаем все сущности типа Creature из карты и выполняем для каждой перемещение
        gameMap.getEntitiesOfType(Creature.class)
                .entrySet()
                .forEach(entry-> moveCreature(entry.getKey(),entry.getValue(),gameMap));
    }

    private void moveCreature(Coordinates coordinates, Creature creature, GameMap gameMap) {
        creature.makeMove(coordinates,gameMap);
    }

}
