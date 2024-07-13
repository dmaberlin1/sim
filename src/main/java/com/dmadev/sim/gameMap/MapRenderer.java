package com.dmadev.sim.gameMap;

import com.dmadev.sim.base.Entity;
import com.dmadev.sim.constants.Colors;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class MapRenderer {
    private static final String WHITE_BACKGROUND = Colors.WHITE_BACKGROUND.getValue();
    private static final String RESET_BACKGROUND = Colors.RESET_BACKGROUND.getValue();

    /**
     * Отображает текущее состояние игровой карты в консоли.
     *
     * @param gameMap игровая карта, которую необходимо отобразить
     */
    public void render(GameMap gameMap) {
        IntStream.rangeClosed(0, GameMap.MAP_HEIGHT)
                .forEach(height -> {
                    String line = IntStream.rangeClosed(0, GameMap.MAP_WIDTH)
                            .mapToObj(weight -> {
                                Coordinates coordinates = new Coordinates(height, weight);
                                return gameMap.isPlaceEmpty(coordinates) ?
                                        getIconForEmptySquare() :
                                        getEntityIcon(gameMap.getEntity(coordinates));
                            })
                            .collect(Collectors.joining()) + RESET_BACKGROUND;
                    System.out.println(line);
                });

    }

    /**
     * Окрашивает иконку в белый цвет фона.
     *
     * @param icon иконка, которую необходимо окрасить
     * @return окрашенная иконка
     */
    private String colorizeIcon(String icon) {
        return WHITE_BACKGROUND + icon;
    }


    /**
     * Возвращает цветную иконку сущности.
     *
     * @param entity сущность, для которой необходимо получить иконку
     * @return цветная иконка сущности
     */
    private String getEntityIcon(Entity entity) {
        return colorizeIcon(selectUnicodeIconForEntity(entity));
    }

    /**
     * Выбирает Unicode иконку для указанной сущности в зависимости от её типа.
     *
     * @param entity сущность, для которой необходимо выбрать Unicode иконку
     * @return Unicode иконка для указанной сущности
     */
    private String selectUnicodeIconForEntity(Entity entity) {
        return Colors.getValueByEntityType(entity.getType());
    }

    /**
     * Возвращает иконку для пустого места на игровой карте.
     *
     * @return иконка для пустого места
     */
    private String getIconForEmptySquare() {
        return colorizeIcon(Colors.EMPTY_SQUARE.getValue());
    }
}
