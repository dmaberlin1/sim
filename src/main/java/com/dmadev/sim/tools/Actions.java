package com.dmadev.sim.tools;

import org.springframework.stereotype.Service;

//    Actions - список действий, исполняемых перед стартом симуляции или на каждом ходу (детали ниже)
@Service
public class Actions {

//    initActions - действия, совершаемые перед стартом симуляции. Пример - расставить объекты и существ на карте
    public void initActions(){

    }

//    turnActions - действия,
//    совершаемые каждый ход. Примеры - передвижение существ, добавить травы или травоядных, если их осталось слишком мало
//Поиск пути
//    Советую писать алгоритм поиска пути полностью с нуля,
//    используя в качестве источника описание алгоритма на википедии. Проще всего начать с алгоритма поиска в ширину.
    public void turnActions(){

    }



//    nextTurn() - просимулировать и отрендерить один ход
    public void nextTurn(){

    }

//    startSimulation() - запустить бесконечный цикл симуляции и рендеринга
    public void startSimulation(){

    }
//    pauseSimulation() - приостановить бесконечный цикл симуляции и рендеринга
    public void pauseSimulation(){

    }
}
