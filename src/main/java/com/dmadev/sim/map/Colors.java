package com.dmadev.sim.map;

import com.dmadev.sim.base.EntityType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Getter
@AllArgsConstructor
public enum Colors {

    RESET_BACKGROUND("ResetBackground", "\033[0m", null),
    WHITE_BACKGROUND("WhiteBackground", "\033[0;107m", null),
    EMPTY_SQUARE("EmptySquare", "▫️", null),
    ROCK("Rock", "\uD83E\uDEA8", EntityType.ROCK),
    GRASS("Grass", "\uD83C\uDF3F", EntityType.GRASS),
    HERBIVORE("Herbivore", "\uD83D\uDC30", EntityType.HERBIVORE),
    PREDATOR("Predator", "\uD83D\uDC3A", EntityType.PREDATOR);
    private final String name;
    private final String value;
    // Тип сущности, к которому относится данный цвет
    private final EntityType entityType;


    /**
     * Метод для получения значения цвета по типу сущности.
     *
     * @param entityType Тип сущности, для которого нужно получить значение цвета.
     * @return Значение цвета, соответствующее данному типу сущности, или пустая строка, если тип не найден.
     */
    public static String getValueByEntityType(EntityType entityType) {
        return Arrays.stream(Colors.values())
                .filter(color -> color.getEntityType() == entityType)
                .map(Colors::getValue)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Entity not found"));
    }

}
