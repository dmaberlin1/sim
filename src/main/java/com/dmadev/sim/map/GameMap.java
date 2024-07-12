package com.dmadev.sim.map;

import com.dmadev.sim.base.Entity;
import com.dmadev.sim.base.Params;
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

    // Проверяет, свободно ли место на указанных координатах
    public boolean isPlaceEmpty(Coordinates coordinates) {
        return !entities.containsKey(coordinates);
    }

    public Entity getEntity(Coordinates coordinates) {
        return entities.get(coordinates);
    }

    // Устанавливает сущность на указанных координатах
    public void setEntity(Coordinates coordinates, Entity entity) {
        // Обновляем координаты сущности
        entity.coordinates = coordinates;
        // Добавляем сущность в карту
        entities.put(coordinates, entity);
    }

    public void removeEntity(Coordinates coordinates) {
        entities.remove(coordinates);
    }

    public void moveEntity(Coordinates from, Coordinates to) {
        Entity entity = getEntity(from);
        entities.remove(from);
        setEntity(to, entity);
    }

    public Coordinates getEmptyPlaceRandom() {
        while (true) {
            // Генерируем случайные координаты в пределах высоты и ширины карты
            Coordinates coordinates = new Coordinates(
                    random.nextInt(MAP_HEIGHT + 1),
                    random.nextInt(MAP_WIDTH + 1));
            if (isPlaceEmpty(coordinates)) {
                return coordinates;
            }
        }
    }

    // Возвращает всех сущностей указанного типа на карте
    public <T> HashMap<Coordinates, T> getEntitiesOfType(Class<T> typeEntity) {

        //Проходим по всем сущностям на карте
        return entities.entrySet().stream()
                // Фильтрует записи, оставляя только те, где значение является экземпляром указанного типа
                .filter(entry -> typeEntity.isInstance(entry.getValue()))
                .collect(Collectors.toMap(
                        // Использует ключ (координаты) как ключ в результирующей карте
                        Map.Entry::getKey,
                        // Преобразует значение записи в указанный тип и использует его как значение в результирующей карте
                        entry -> typeEntity.cast(entry.getValue()),
                        // Разрешает конфликты ключей путем выбора первого значения (в данном случае конфликты маловероятны)
                        (e1, e2) -> e1,
                        // Указывает, что результат должен быть HashMap
                        HashMap::new));
    }

    //eof
}