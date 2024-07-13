package com.dmadev.sim.gameMap;

import com.dmadev.sim.base.Entity;
import com.dmadev.sim.constants.Params;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;


@Component
public class GameMap {
    public static final int MAP_HEIGHT = Params.MAP_HEIGHT.getValue();
    public static final int MAP_WIDTH = Params.MAP_WIDTH.getValue();
    private static final Random random = new Random();
    private final HashMap<Coordinates, Entity> entities = new HashMap<>();

    /**
     * Проверяет, свободно ли место на указанных координатах.
     *
     * @param coordinates координаты для проверки
     * @return true, если на указанных координатах нет сущности, иначе false
     */
    public boolean isPlaceEmpty(Coordinates coordinates) {
        return !entities.containsKey(coordinates);
    }

    /**
     * Возвращает сущность на указанных координатах.
     *
     * @param coordinates координаты для поиска сущности
     * @return сущность на указанных координатах или null, если там нет сущности
     */
    public Entity getEntity(Coordinates coordinates) {
        return entities.get(coordinates);
    }

    /**
     * Устанавливает сущность на указанных координатах.
     *
     * @param coordinates координаты, на которые следует установить сущность
     * @param entity      сущность, которую следует установить
     */
    public void setEntity(Coordinates coordinates, Entity entity) {
        entity.coordinates = coordinates;
        entities.put(coordinates, entity);
    }

    /**
     * Удаляет сущность с указанных координат.
     *
     * @param coordinates координаты, с которых следует удалить сущность
     */
    public void removeEntity(Coordinates coordinates) {
        entities.remove(coordinates);
    }

    /**
     * Перемещает сущность с одних координат на другие.
     *
     * @param from начальные координаты сущности
     * @param to   конечные координаты, на которые следует переместить сущность
     */
    public void moveEntity(Coordinates from, Coordinates to) {
        Entity entity = getEntity(from);
        entities.remove(from);
        setEntity(to, entity);
    }

    /**
     * Возвращает случайные пустые координаты на карте.
     *
     * @return случайные пустые координаты
     */
    public Coordinates getEmptyPlaceRandom() {
        while (true) {
            Coordinates coordinates = new Coordinates(
                    random.nextInt(MAP_HEIGHT + 1),
                    random.nextInt(MAP_WIDTH + 1));
            if (isPlaceEmpty(coordinates)) {
                return coordinates;
            }
        }
    }

    /**
     * Возвращает все сущности указанного типа на карте.
     *
     * @param typeEntity класс типа сущности
     * @param <T>        тип сущности
     * @return карта с координатами и сущностями указанного типа
     */
    public <T> HashMap<Coordinates, T> getEntitiesOfType(Class<T> typeEntity) {

        return entities.entrySet().stream()
                .filter(entry -> typeEntity.isInstance(entry.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> typeEntity.cast(entry.getValue()),
                        (e1, e2) -> e1,
                        HashMap::new));
    }

    //eof
}