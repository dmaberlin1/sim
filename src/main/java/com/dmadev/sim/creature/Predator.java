package com.dmadev.sim.creature;

import com.dmadev.sim.base.Creature;
import com.dmadev.sim.constants.EntityType;
import com.dmadev.sim.gameMap.Coordinates;
import com.dmadev.sim.gameMap.GameMap;


public final class Predator extends Creature {
    private final int power;

    public Predator(Coordinates coordinates, int power) {
        super(EntityType.PREDATOR, coordinates);
        this.power = power;
    }

    /**
     * Метод, позволяющий хищнику атаковать и съесть травоядное существо на указанных координатах.
     * Если существо умирает от атаки, вызывается метод eat у родительского класса.
     * Если остается здоровье у атакованного существа, уменьшается его здоровье.
     *
     * @param start   начальные координаты хищника
     * @param to      координаты атаки (где находится травоядное существо)
     * @param gameMap игровая карта, на которой происходит симуляция
     */
    public void eat(Coordinates start, Coordinates to, GameMap gameMap) {
        Creature herbivore = (Creature) gameMap.getEntity(to);
        int remainingHealth = herbivore.getHealth() - power;

        if (remainingHealth <= 0) {
            super.eat(start, to, gameMap);
        } else {
            herbivore.setHealth(remainingHealth);
        }
    }

}
