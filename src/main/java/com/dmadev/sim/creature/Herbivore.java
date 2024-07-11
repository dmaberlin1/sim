package com.dmadev.sim.creature;

import com.dmadev.sim.base.Creature;
import com.dmadev.sim.map.Coordinates;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;




public final class Herbivore extends Creature {
    public Herbivore(Coordinates coordinates) {
        super(coordinates);
    }


    //Травоядное, наследуется от Creature.
    // Стремятся найти ресурс (траву),
    // может потратить свой ход на движение в сторону травы, либо на её поглощение.
}
