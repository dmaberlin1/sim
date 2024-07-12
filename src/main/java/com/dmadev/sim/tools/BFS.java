package com.dmadev.sim.tools;

import com.dmadev.sim.base.Creature;
import com.dmadev.sim.base.Entity;
import com.dmadev.sim.base.Params;
import com.dmadev.sim.creature.Herbivore;
import com.dmadev.sim.creature.Predator;
import com.dmadev.sim.map.Coordinates;
import com.dmadev.sim.map.GameMap;
import com.dmadev.sim.nature.Grass;
import com.dmadev.sim.nature.Rock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.function.Predicate;

public class BFS {
    static Class<? extends Entity> victim;


    // Основной метод для нахождения кратчайшего пути до целевой сущности
    public static List<Coordinates> get(Coordinates startNode, GameMap gameMap, Class<? extends Entity> victim) {
        // Устанавливаем целевую сущность для поиска
        BFS.victim = victim;
        return findShortPathToResult(startNode, gameMap);
    }

    private static List<Coordinates> findShortPathToResult(Coordinates startNode, GameMap gameMap) {
        // Очередь для обхода узлов
        Queue<Coordinates> queue = new LinkedList<>();
        // Карта для хранения родительских узлов
        Map<Coordinates, Coordinates> parentMap = new HashMap<>();
        // Множество для отслеживания посещенных узлов
        Set<Coordinates> visitedSet = new HashSet<>();
        // Узел, содержащий целевую сущность
        Coordinates resultNode = null;

        // Запускаем обход в ширину
        queue.add(startNode);
        resultNode = breadthFirstSearch(queue, parentMap, visitedSet, gameMap);

        // Строим путь от целевого узла к начальному узлу
        return buildPath(resultNode, parentMap);
    }

    private static Coordinates breadthFirstSearch(Queue<Coordinates> queue, Map<Coordinates, Coordinates> parentMap,
                                                  Set<Coordinates> visitedSet, GameMap gameMap) {


        while (!queue.isEmpty()) {
            Coordinates current = queue.remove();
            visitedSet.add(current);

            Optional<Coordinates> optionalResultNode = Optional.ofNullable(checkAndReturnVictimNode(current, gameMap));
            if (optionalResultNode.isPresent()) {
                return optionalResultNode.get();
            }
            processNeighborNodes(queue, parentMap, visitedSet, gameMap, current);
        }
        return null;
    }


    private static void processNeighborNodes(Queue<Coordinates> queue, Map<Coordinates, Coordinates> parentMap, Set<Coordinates> visitedSet, GameMap gameMap, Coordinates current) {
        getNodesWithinBorders(current, gameMap).stream()
                .filter(node -> !visitedSet.contains(node) && !queue.contains(node))
                .forEach(node -> {
                    processNeighborNode(queue, parentMap, visitedSet, node, current);
                });
    }
    private static void processNeighborNode(Queue<Coordinates> queue, Map<Coordinates, Coordinates> parentMap, Set<Coordinates> visitedSet, Coordinates node, Coordinates current) {
        parentMap.put(node, current);
        queue.offer(node);
        visitedSet.add(node);
    }

    private static Coordinates checkAndReturnVictimNode(Coordinates current, GameMap gameMap) {
        if (isVictimNode(current, gameMap)) {
            return current;
        }
        return null;
    }




    private static List<Coordinates> buildPath(Coordinates resultNode, Map<Coordinates, Coordinates> parentMap) {
        List<Coordinates> path = new ArrayList<>();

        while (resultNode != null) {
            path.add(resultNode);
            resultNode = parentMap.get(resultNode);
        }
        Collections.reverse(path);
        return path;
    }


    // Метод для получения соседних узлов в пределах границ карты
    private static List<Coordinates> getNodesWithinBorders(Coordinates current, GameMap gameMap) {
        int cordY = current.getY();
        int cordX = current.getX();


        Predicate<Coordinates> isWithinBorders = coords -> isWithinVerticalBorders(coords) && isWithinHorizontalBorders(coords);
        Predicate<Coordinates> isNotBarrier = coords -> isNotABarrier(coords, gameMap);


        // Генерируем и фильтруем соседние узлы в пределах границ карты
        return Arrays.stream(new Coordinates[]{
                        new Coordinates(cordY - 1, cordX), // Вверх
                        new Coordinates(cordY, cordX + 1), // Вправо
                        new Coordinates(cordY + 1, cordX), // Вниз
                        new Coordinates(cordY, cordX - 1)   // Влево
                })
                // Фильтруем узлы, оставляя только те, которые находятся в пределах карты и не являются барьерами
                .filter(isWithinBorders.and(isNotBarrier))
                .toList();
    }


    private static boolean isVictimNode(Coordinates current, GameMap gameMap) {
        return !gameMap.isPlaceEmpty(current) && gameMap.getEntity(current).getClass() == victim;
    }


    private static boolean isWithinVerticalBorders(Coordinates coords) {
        return coords.getY() >= 0 && coords.getY() <= Params.MAP_HEIGHT.getValue();
    }

    private static boolean isWithinHorizontalBorders(Coordinates coords) {
        return coords.getX() >= 0 && coords.getX() <= Params.MAP_WIDTH.getValue();
    }


    private static boolean isNotABarrier(Coordinates coords, GameMap gameMap) {
        // Получаем сущность по указанным координатам
        Entity entity = gameMap.getEntity(coords);
        // Проверяем, является ли сущность барьером, исходя из типа victim
        return victim == Herbivore.class ? isNotHerbivoreBarrier(entity) : isNotGeneralBarrier(entity);
    }


    // Метод для проверки, является ли сущность барьером для травоядных
    private static boolean isNotHerbivoreBarrier(Entity entity) {
        // Возвращаем true, если сущность не является хищником, камнем или травой
        return !(isPredator(entity) || isRock(entity) || isGrass(entity));
    }

    // Метод для проверки, является ли сущность общим барьером
    private static boolean isNotGeneralBarrier(Entity entity) {
        // Возвращаем true, если сущность не является существом или камнем
        return !(isCreature(entity) || isRock(entity));
    }


    private static boolean isPredator(Entity entity) {
        return entity instanceof Predator;
    }

    private static boolean isRock(Entity entity) {
        return entity instanceof Rock;
    }

    private static boolean isGrass(Entity entity) {
        return entity instanceof Grass;
    }

    private static boolean isCreature(Entity entity) {
        return entity instanceof Creature;
    }

//eof
}
