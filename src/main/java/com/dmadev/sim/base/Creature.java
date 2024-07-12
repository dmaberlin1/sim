package com.dmadev.sim.base;


import com.dmadev.sim.creature.Herbivore;
import com.dmadev.sim.creature.Predator;
import com.dmadev.sim.map.Coordinates;
import com.dmadev.sim.map.GameMap;
import com.dmadev.sim.nature.Grass;
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


    protected void decreaseHealth() {
        health--;
    }

    protected void eat(Coordinates start, Coordinates to, GameMap gameMap) {
        gameMap.moveEntity(start, to);
        health = Math.min(health + 16, maxHealth);
    }

    public Creature(EntityType type,Coordinates coordinates) {
        super(type,coordinates);
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

    // Использует алгоритм поиска в ширину для нахождения пути к жертве
    private List<Coordinates> findPath(Coordinates start, GameMap gameMap) {
        return BFS.get(start, gameMap, victim);
    }

    private Coordinates determineDestination(List<Coordinates> path) {
        // Ограничиваем скорость длиной пути или максимальной скоростью существа
        // path.size() - 1 используется для корректного получения индекса последней координаты в списке
        // Поскольку индексация в списке начинается с нуля, последний индекс будет
        int amountOfSpeed = Math.min(getSpeed(), path.size() - 1);

        //final coordinates
        return path.get(amountOfSpeed);
    }

    // Проверяет, является ли конечная координата допустимой для перемещения
    private boolean isTargetValid(Coordinates to, GameMap gameMap) {
        // Возвращает true, если конечная координата не null и на ней нет существа того же типа
        if(to==null){
           return false;
        }
       Entity targetEntity= gameMap.getEntity(to);
        return !this.getClass().isInstance(targetEntity);

    //return to != null && !this.getClass().isInstance(gameMap.getEntity(to));
    }

    // Проверяет, находится ли жертва на конечной координате
    private boolean isVictim(Coordinates to, GameMap gameMap) {
        return victim.isInstance(gameMap.getEntity(to));
    }

    // Либо перемещает существо, либо заставляет его съесть жертву
    private void moveEntityOrEat(Coordinates start, Coordinates to, GameMap gameMap) {
        if (isVictim(to, gameMap)) {
            // Если на конечной координате жертва, Predator ее съедает
            eat(start, to, gameMap);
        } else {
            gameMap.moveEntity(start, to);
        }
    }

    private void handleMove(Coordinates start, GameMap gameMap) {
        List<Coordinates> path = findPath(start, gameMap);
        if (path.size() > 1) {
            Coordinates to = determineDestination(path);
            if (isTargetValid(to, gameMap)) {
                moveEntityOrEat(start, to, gameMap);
            }
        }
    }

    public void makeMove(Coordinates start, GameMap gameMap) {
        if (isAlive()) {
            handleMove(start, gameMap);
            decreaseHealth();
        } else {
            gameMap.removeEntity(start);
        }
    }
}


//eof
}
