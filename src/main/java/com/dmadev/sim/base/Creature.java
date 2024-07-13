package com.dmadev.sim.base;


import com.dmadev.sim.constants.Constants;
import com.dmadev.sim.constants.EntityType;
import com.dmadev.sim.constants.Params;
import com.dmadev.sim.creature.Herbivore;
import com.dmadev.sim.creature.Predator;
import com.dmadev.sim.gameMap.Coordinates;
import com.dmadev.sim.gameMap.GameMap;
import com.dmadev.sim.creature.nature.Grass;
import com.dmadev.sim.tools.BFS;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Setter
@Getter

public abstract class Creature extends Entity {
    private final Class<? extends Entity> victim;
    protected final int speed;
    private int maxHealth;
    protected int health;

    /**
     * Уменьшает текущее здоровье существа на единицу.
     */
    protected void decreaseHealth() {
        health--;
    }
    /**
     * Выполняет съедение жертвы с увеличением здоровья существа.
     *
     * @param start   начальные координаты существа
     * @param to      координаты атаки (где находится жертва)
     * @param gameMap игровая карта, на которой происходит симуляция
     */
    protected void eat(Coordinates start, Coordinates to, GameMap gameMap) {
        gameMap.moveEntity(start, to);
        health = Math.min(health + Constants.PREDATOR_HEALTH_AFTER_EAT, maxHealth);
    }

    public Creature(EntityType type, Coordinates coordinates) {
        super(type, coordinates);
        if (this instanceof Predator) {
            victim = Herbivore.class;
            speed = Params.PREDATOR_SPEED.getValue();
            health = Params.PREDATOR_HEALTH.getValue();
        } else {
            victim = Grass.class;
            speed = Params.HERBIVORE_SPEED.getValue();
            health = Params.HERBIVORE_HEALTH.getValue();

        }
        this.maxHealth = health;
    }


    public boolean isAlive() {
        return health > 0;
    }
    /**
     * Использует алгоритм поиска в ширину для нахождения пути к целевой сущности.
     *
     * @param start   начальные координаты существа
     * @param gameMap игровая карта, на которой происходит симуляция
     * @return список координат пути до цели
     */
    private List<Coordinates> findPath(Coordinates start, GameMap gameMap) {
        return BFS.get(start, gameMap, victim);
    }
    /**
     * Определяет конечную точку перемещения существа в зависимости от пути и его скорости.
     *
     * @param path список координат пути до цели
     * @return конечные координаты, куда существо собирается переместиться
     */
    private Coordinates determineDestination(List<Coordinates> path) {
        int amountOfSpeed = Math.min(getSpeed(), path.size() - 1);

        return path.get(amountOfSpeed);
    }
    /**
     * Проверяет, является ли конечная координата допустимой для перемещения.
     *
     * @param to      конечные координаты, куда существо собирается переместиться
     * @param gameMap игровая карта, на которой происходит симуляция
     * @return true, если конечная координата не null и не занята существом того же типа, иначе false
     */
    private boolean isTargetValid(Coordinates to, GameMap gameMap) {
        if (to == null) {
            return false;
        }
        Entity targetEntity = gameMap.getEntity(to);
        return !this.getClass().isInstance(targetEntity);

    }
    /**
     * Проверяет, является ли на конечной координате жертва.
     *
     * @param to      конечные координаты, куда существо собирается переместиться
     * @param gameMap игровая карта, на которой происходит симуляция
     * @return true, если на конечной координате находится жертва, иначе false
     */
    private boolean isVictim(Coordinates to, GameMap gameMap) {
        return victim.isInstance(gameMap.getEntity(to));
    }

    /**
     * Выполняет перемещение или атаку существа в зависимости от обстоятельств.
     *
     * @param start   начальные координаты существа
     * @param to      конечные координаты, куда существо собирается двигаться
     * @param gameMap игровая карта, на которой происходит симуляция
     */
    private void moveEntityOrEat(Coordinates start, Coordinates to, GameMap gameMap) {
        if (isVictim(to, gameMap)) {
            // Если на конечной координате жертва, Predator ее съедает
            eat(start, to, gameMap);
        } else {
            gameMap.moveEntity(start, to);
        }
    }

    /**
     * Обрабатывает ход существа, вычисляя путь и выполняя действия в зависимости от обстоятельств.
     *
     * @param start   начальные координаты существа
     * @param gameMap игровая карта, на которой происходит симуляция
     */
    private void handleMove(Coordinates start, GameMap gameMap) {
        List<Coordinates> path = findPath(start, gameMap);
        if (path.size() > 1) {
            Coordinates to = determineDestination(path);
            if (isTargetValid(to, gameMap)) {
                moveEntityOrEat(start, to, gameMap);
            }
        }
    }

    /**
     * Метод для выполнения хода существом.
     * Перемещает существо или заставляет его съесть жертву в зависимости от обстоятельств.
     *
     * @param start   начальные координаты существа
     * @param gameMap игровая карта, на которой происходит симуляция
     */
    public void makeMove(Coordinates start, GameMap gameMap) {
        if (isAlive()) {
            handleMove(start, gameMap);
            decreaseHealth();
        } else {
            gameMap.removeEntity(start);
        }
    }

    //eof
}