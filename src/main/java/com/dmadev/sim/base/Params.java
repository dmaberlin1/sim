package com.dmadev.sim.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Params {
    PREDATOR_SPEED("PredatorSpeed",1),
    PREDATOR_HEALTH("PredatorHealth",48),
    HERBIVORE_SPEED("HerbivoreSpeed",2),
    HERBIVORE_HEALTH("HerbivoreHealth",20),
    MAP_HEIGHT("MapHeight",9),
    MAP_WIDTH("MapHeight",9);

    private final String name;
    private final int value;
}
