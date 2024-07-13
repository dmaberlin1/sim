package com.dmadev.sim.service;

import com.dmadev.sim.action.GrassSpawnAction;
import com.dmadev.sim.action.HerbivoreSpawnAction;
import com.dmadev.sim.action.MoveAction;
import com.dmadev.sim.action.PredatorSpawnAction;
import com.dmadev.sim.action.RockSpawnAction;
import com.dmadev.sim.action.SpawnAction;
import com.dmadev.sim.constants.Constants;
import com.dmadev.sim.creature.Herbivore;
import com.dmadev.sim.creature.Predator;
import com.dmadev.sim.gameMap.Coordinates;
import com.dmadev.sim.gameMap.GameMap;
import com.dmadev.sim.gameMap.MapRenderer;
import com.dmadev.sim.creature.nature.Grass;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class Simulation {

    private final GameMap gameMap;
    private final MapRenderer mapRenderer;
    private final MoveAction moveAction;
    private static final AtomicInteger countIteration = new AtomicInteger(1);

    /**
     * Выполняет следующий шаг симуляции:
     * - Выполняет движение существ на карте.
     * - Отображает карту.
     * - Проверяет наличие живых существ и завершает симуляцию при условии победы одной из сторон.
     * - Добавляет траву на карту при необходимости.
     */
    public void nextStep() {
        moveAction.perform(gameMap);
        mapRenderer.render(gameMap);
        checkLivingCreatures(gameMap);
        addGrassIfNeeded(gameMap);
    }

    /**
     * Проверяет наличие живых существ на карте.
     * Если травоядные или хищники отсутствуют, выводит соответствующее сообщение о победе и завершает программу.
     *
     * @param gameMap игровая карта
     */
    private void checkLivingCreatures(GameMap gameMap) {
        if (gameMap.getEntitiesOfType(Herbivore.class).isEmpty()) {
            System.out.println(Constants.HERBIVORE_WIN);
            System.exit(0);
        }
        if (gameMap.getEntitiesOfType(Predator.class).isEmpty()) {
            System.out.println(Constants.PREDATOR_WIN);
            System.exit(0);

        }

    }

    /**
     * Добавляет траву на карту, если текущее количество травы меньше заданного лимита (60).
     *
     * @param gameMap игровая карта
     */
    private void addGrassIfNeeded(GameMap gameMap) {
        int grassCount = gameMap.getEntitiesOfType(Grass.class).size();
        // Считаем количество травы, недостающее до 60
        int endExclusive = Constants.GRASS_LIMIT - grassCount;
        IntStream.range(0, endExclusive)
                .forEach(i -> addGrassToMap(gameMap));
    }

    /**
     * Добавляет одну траву на случайное место на карте.
     *
     * @param gameMap игровая карта
     */
    private void addGrassToMap(GameMap gameMap) {
        Coordinates coordinates = gameMap.getEmptyPlaceRandom();
        gameMap.setEntity(coordinates, new Grass(coordinates));
    }

    /**
     * Возвращает текущий номер итерации симуляции и увеличивает его на 1.
     *
     * @return текущий номер итерации
     */
    public int getCountIteration() {
        return countIteration.getAndIncrement();
    }

    /**
     * Возвращает список инициализирующих действий для начала симуляции.
     *
     * @return список инициализирующих действий
     */
    private List<SpawnAction<?>> getInitActions() {
        List<SpawnAction<?>> initActions = new ArrayList<>();
        initActions.add(new GrassSpawnAction());
        initActions.add(new HerbivoreSpawnAction());
        initActions.add(new PredatorSpawnAction());
        initActions.add(new RockSpawnAction());

        return initActions;
    }

    /**
     * Инициализирует мир симуляции, выполняя все необходимые действия по размещению начальных объектов на карте.
     */
    public void initWorld() {
        Consumer<SpawnAction<?>> spawnActionConsumer = spawnAction -> spawnAction.perform(gameMap);
        getInitActions().forEach(spawnActionConsumer);
        System.out.println(Constants.GAME_MAP_GENERATED);
    }

}
