package com.dmadev.sim.tools;

import com.dmadev.sim.base.Creature;
import com.dmadev.sim.base.Entity;
import com.dmadev.sim.constants.Params;
import com.dmadev.sim.creature.Herbivore;
import com.dmadev.sim.creature.Predator;
import com.dmadev.sim.gameMap.Coordinates;
import com.dmadev.sim.gameMap.GameMap;
import com.dmadev.sim.creature.nature.Grass;
import com.dmadev.sim.creature.nature.Rock;

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


    /**
     * Основной метод для нахождения кратчайшего пути до целевой сущности.
     *
     * @param startNode начальная точка поиска
     * @param gameMap   игровая карта, на которой выполняется поиск
     * @param victim    класс целевой сущности для поиска
     * @return список координат пути от startNode до найденной целевой сущности
     */
    public static List<Coordinates> get(Coordinates startNode, GameMap gameMap, Class<? extends Entity> victim) {
        BFS.victim = victim;
        return findShortPathToResult(startNode, gameMap);
    }

    /**
     * Приватный метод для выполнения поиска в ширину до найденной целевой сущности.
     *
     * @param startNode начальная точка поиска
     * @param gameMap   игровая карта, на которой выполняется поиск
     * @return координаты узла с найденной целевой сущностью или null, если не найдено
     */
    private static List<Coordinates> findShortPathToResult(Coordinates startNode, GameMap gameMap) {
        Queue<Coordinates> queue = new LinkedList<>();
        Map<Coordinates, Coordinates> parentMap = new HashMap<>();
        Set<Coordinates> visitedSet = new HashSet<>();
        Coordinates resultNode = null;

        queue.add(startNode);
        resultNode = breadthFirstSearch(queue, parentMap, visitedSet, gameMap);

        return buildPath(resultNode, parentMap);
    }

    /**
     * Приватный метод для выполнения обхода в ширину на игровой карте.
     *
     * @param queue      очередь для обхода узлов
     * @param parentMap  карта для хранения родительских узлов
     * @param visitedSet множество для отслеживания посещенных узлов
     * @param gameMap    игровая карта, на которой выполняется поиск
     * @return координаты узла с найденной целевой сущностью или null, если не найдено
     */
    private static Coordinates breadthFirstSearch(Queue<Coordinates> queue,
                                                  Map<Coordinates,
                                                          Coordinates> parentMap,
                                                  Set<Coordinates> visitedSet,
                                                  GameMap gameMap) {

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


    /**
     * Приватный метод для обработки соседних узлов игровой карты.
     *
     * @param queue      очередь для обхода узлов
     * @param parentMap  карта для хранения родительских узлов
     * @param visitedSet множество для отслеживания посещенных узлов
     * @param gameMap    игровая карта, на которой выполняется поиск
     * @param current    текущий узел
     */
    private static void processNeighborNodes(Queue<Coordinates> queue, Map<Coordinates, Coordinates> parentMap, Set<Coordinates> visitedSet, GameMap gameMap, Coordinates current) {
        getNodesWithinBorders(current, gameMap).stream()
                .filter(node -> !visitedSet.contains(node) && !queue.contains(node))
                .forEach(node -> {
                    processNeighborNode(queue, parentMap, visitedSet, node, current);
                });
    }

    /**
     * Приватный метод для обработки конкретного соседнего узла игровой карты.
     *
     * @param queue      очередь для обхода узлов
     * @param parentMap  карта для хранения родительских узлов
     * @param visitedSet множество для отслеживания посещенных узлов
     * @param node       текущий обрабатываемый узел
     * @param current    текущий узел
     */
    private static void processNeighborNode(Queue<Coordinates> queue, Map<Coordinates, Coordinates> parentMap, Set<Coordinates> visitedSet, Coordinates node, Coordinates current) {
        parentMap.put(node, current);
        queue.offer(node);
        visitedSet.add(node);
    }

    /**
     * Приватный метод для проверки текущего узла на наличие целевой сущности.
     *
     * @param current текущий узел для проверки
     * @param gameMap игровая карта, на которой выполняется поиск
     * @return найденный узел с целевой сущностью или null, если не найдено
     */
    private static Coordinates checkAndReturnVictimNode(Coordinates current, GameMap gameMap) {
        if (isVictimNode(current, gameMap)) {
            return current;
        }
        return null;
    }

    /**
     * Приватный метод для построения пути от целевого узла к начальному узлу.
     *
     * @param resultNode найденный узел с целевой сущностью
     * @param parentMap  карта для хранения родительских узлов
     * @return список координат пути от целевого узла к начальному узлу
     */
    private static List<Coordinates> buildPath(Coordinates resultNode, Map<Coordinates, Coordinates> parentMap) {
        List<Coordinates> path = new ArrayList<>();

        while (resultNode != null) {
            path.add(resultNode);
            resultNode = parentMap.get(resultNode);
        }
        Collections.reverse(path);
        return path;
    }


    /**
     * Приватный метод для получения соседних узлов в пределах границ карты.
     *
     * @param current текущий узел, для которого находятся соседние узлы
     * @param gameMap игровая карта, на которой выполняется поиск
     * @return список соседних координатных узлов
     */
    private static List<Coordinates> getNodesWithinBorders(Coordinates current, GameMap gameMap) {
        int cordY = current.y();
        int cordX = current.x();
        Predicate<Coordinates> isWithinBorders = coords -> isWithinVerticalBorders(coords) && isWithinHorizontalBorders(coords);
        Predicate<Coordinates> isNotBarrier = coords -> isNotABarrier(coords, gameMap);

        return Arrays.stream(new Coordinates[]{
                        new Coordinates(cordY - 1, cordX), // Вверх
                        new Coordinates(cordY, cordX + 1), // Вправо
                        new Coordinates(cordY + 1, cordX), // Вниз
                        new Coordinates(cordY, cordX - 1)   // Влево
                })
                .filter(isWithinBorders.and(isNotBarrier))
                .toList();
    }


    /**
     * Приватный метод для проверки, является ли текущий узел целевой сущностью.
     *
     * @param current текущий узел для проверки
     * @param gameMap игровая карта, на которой выполняется поиск
     * @return true, если текущий узел содержит целевую сущность, иначе false
     */
    private static boolean isVictimNode(Coordinates current, GameMap gameMap) {
        return !gameMap.isPlaceEmpty(current) && gameMap.getEntity(current).getClass() == victim;
    }


    /**
     * Приватный метод для проверки, находится ли узел в вертикальных границах карты.
     *
     * @param coords координаты текущего узла
     * @return true, если узел находится в вертикальных границах карты, иначе false
     */
    private static boolean isWithinVerticalBorders(Coordinates coords) {
        return coords.y() >= 0 && coords.y() <= Params.MAP_HEIGHT.getValue();
    }

    /**
     * Приватный метод для проверки, находится ли узел в горизонтальных границах карты.
     *
     * @param coords координаты текущего узла
     * @return true, если узел находится в горизонтальных границах карты, иначе false
     */
    private static boolean isWithinHorizontalBorders(Coordinates coords) {
        return coords.x() >= 0 && coords.x() <= Params.MAP_WIDTH.getValue();
    }


    /**
     * Приватный метод для проверки, является ли указанный узел барьером на игровой карте.
     *
     * @param coords  координаты текущего узла
     * @param gameMap игровая карта, на которой выполняется проверка
     * @return true, если узел не является барьером, иначе false
     */
    private static boolean isNotABarrier(Coordinates coords, GameMap gameMap) {
        Entity entity = gameMap.getEntity(coords);
        return victim == Herbivore.class ? isNotHerbivoreBarrier(entity) : isNotGeneralBarrier(entity);
    }

    /**
     * Приватный метод для проверки, является ли сущность барьером для травоядных.
     *
     * @param entity сущность для проверки
     * @return true, если сущность не является хищником, камнем или травой, иначе false
     */
    private static boolean isNotHerbivoreBarrier(Entity entity) {
        return !(isPredator(entity) || isRock(entity) || isGrass(entity));
    }

    /**
     * Приватный метод для проверки, является ли сущность общим барьером.
     *
     * @param entity сущность для проверки
     * @return true, если сущность не является существом или камнем, иначе false
     */
    private static boolean isNotGeneralBarrier(Entity entity) {
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
