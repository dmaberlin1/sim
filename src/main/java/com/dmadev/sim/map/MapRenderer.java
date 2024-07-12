package com.dmadev.sim.map;

import com.dmadev.sim.base.Entity;
import com.dmadev.sim.base.EntityType;
import com.dmadev.sim.base.Params;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MapRenderer {
    private static final String WHITE_BACKGROUND = Colors.WHITE_BACKGROUND.getValue();
    private static final String RESET_BACKGROUND = Colors.RESET_BACKGROUND.getValue();


    public void render(GameMap gameMap) {
        //Используем IntStream.rangeClosed для итерации по высоте карты от 0 до MAP_HEIGHT.
        IntStream.rangeClosed(0, GameMap.MAP_HEIGHT)
                .forEach(height -> {
                    // Формируем строку для текущей высоты карты. От 0 до MAP_WIDTH
                    String line = IntStream.rangeClosed(0, GameMap.MAP_WIDTH)
                            .mapToObj(weight -> {
                                // Создаем объект координат для текущих координат height и width
                                Coordinates coordinates = new Coordinates(height, weight);
                                // Получаем иконку для пустого места или сущности на данных координатах
                                return gameMap.isPlaceEmpty(coordinates) ?
                                        getIconForEmptySquare() :
                                        getEntityIcon(gameMap.getEntity(coordinates));
                            })
                            // Объединяем все полученные иконки в одну строку и добавляем сброс фона
                            .collect(Collectors.joining()) + RESET_BACKGROUND;
                    System.out.println(line);
                });

    }


    private String colorizeIcon(String unit) {
        return WHITE_BACKGROUND + unit;
    }


    // Метод для получения иконки сущности
    private String getEntityIcon(Entity entity) {
        return colorizeIcon(selectUnicodeIconForEntity(entity));
    }

    // Метод для выбора Unicode иконки в зависимости от типа сущности
    private String selectUnicodeIconForEntity(Entity entity) {
        return Colors.getValueByEntityType(entity.getType());
    }

    private String getIconForEmptySquare() {
        return colorizeIcon(Colors.EMPTY_SQUARE.getValue());
    }
}
