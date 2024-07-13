package com.dmadev.sim.action;

import com.dmadev.sim.base.Creature;
import com.dmadev.sim.gameMap.Coordinates;
import com.dmadev.sim.gameMap.GameMap;
import org.springframework.stereotype.Component;

@Component
public  class MoveAction extends Action {

    /**
     * Выполняет перемещение всех существ типа Creature на игровой карте.
     *
     * @param gameMap игровая карта, на которой выполняется перемещение существ
     */
    @Override
    public  void perform(GameMap gameMap){
        // Получаем все сущности типа Creature из карты и выполняем для каждой перемещение
        gameMap.getEntitiesOfType(Creature.class)
                .entrySet()
                .forEach(entry-> moveCreature(entry.getKey(),entry.getValue(),gameMap));
    }

    /**
     * Выполняет перемещение конкретного существа на указанные координаты на игровой карте.
     *
     * @param coordinates координаты текущего положения существа
     * @param creature    существо, которое необходимо переместить
     * @param gameMap     игровая карта, на которой выполняется перемещение
     */
    private void moveCreature(Coordinates coordinates, Creature creature, GameMap gameMap) {
        creature.makeMove(coordinates,gameMap);
    }

}
