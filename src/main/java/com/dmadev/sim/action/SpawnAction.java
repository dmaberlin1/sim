package com.dmadev.sim.action;

import com.dmadev.sim.base.Entity;
import com.dmadev.sim.gameMap.Coordinates;
import com.dmadev.sim.gameMap.GameMap;

import java.util.stream.IntStream;

/**
 * Абстрактный класс SpawnAction представляет действие спауна сущности на карте.
 * Он реализует выполнение спауна с использованием случайных пустых мест на карте.
 *
 * @param <T> тип сущности, которую необходимо спаунить
 */
public abstract class SpawnAction<T extends Entity> extends Action {
    protected int countTypeOnMap;

    /**
     * Выполняет спаун сущностей на карте.
     *
     * @param gameMap игровая карта, на которой выполняется спаун
     */
    @Override
    public void perform(GameMap gameMap) {
        IntStream.range(0, countTypeOnMap).forEach
                (i -> spawnEntityAtRandomEmptyPlace(gameMap));
    }

    /**
     * Метод для спауна сущности на случайном пустом месте на карте.
     *
     * @param gameMap игровая карта, на которой выполняется спаун
     */
    private void spawnEntityAtRandomEmptyPlace(GameMap gameMap) {
        Coordinates coordinates = gameMap.getEmptyPlaceRandom();
        gameMap.setEntity(coordinates, spawnEntity(coordinates));
    }

    /**
     * Абстрактный метод для создания экземпляра сущности по указанным координатам.
     *
     * @param coordinates координаты, где необходимо создать сущность
     * @return экземпляр созданной сущности
     */
    protected abstract T spawnEntity(Coordinates coordinates);
}
