package com.dmadev.sim.service;

import com.dmadev.sim.constants.Constants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class SimulationService {
    private static final String START_MENU_ITEM = "1";
    private static final String TURN_SIM = "2";
    private static final String EXIT = "3";
    private static final int PAUSE_SIM = 1;
    private static final int CONTINUE_SIM = 2;
    private static final Scanner scanner = new Scanner(System.in);
    Simulation simulation;


    /**
     * Основной метод для запуска симуляции.
     * Инициализирует мир и запускает цикл для обработки ввода пользователя.
     */
    public void Run() {
        simulation.initWorld();
        // Генерация потока ввода пользователя
        Stream.generate(this::getUserInput)
                .takeWhile(input -> !EXIT.equals(input))
                .forEach(this::processUserInput);
    }


    /**
     * Метод для обработки ввода пользователя.
     * Выполняет действия в зависимости от команды пользователя.
     *
     * @param input Ввод пользователя
     */
    private void processUserInput(String input) {
        switch (input) {
            case START_MENU_ITEM -> runAutoSimulation();
            case TURN_SIM -> {
                System.out.println(Constants.ITERATION_NUMBER + simulation.getCountIteration() + ".");
                simulation.nextStep();
            }
        }
    }

    /**
     * Метод для автоматического запуска симуляции.
     * Симуляция продолжается до тех пор, пока не будет получена команда на паузу.
     */
    private void runAutoSimulation() {
        int userInput = CONTINUE_SIM;
        while (userInput != PAUSE_SIM) {
            userInput = inputInSimulation(userInput);
        }
    }

    /**
     * Метод для обработки ввода пользователя во время автоматического запуска симуляции.
     * Проверяет наличие ввода пользователя и приостанавливает симуляцию при необходимости.
     *
     * @param current Текущий статус симуляции
     * @return Обновленный статус симуляции
     */
    private int inputInSimulation(int current) {
        try {
            Thread.sleep(500);
            if (System.in.available() > 0 && scanner.nextInt() == PAUSE_SIM) {
                return PAUSE_SIM;
            }
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
        return current;
    }


    /**
     * Метод для получения ввода пользователя.
     *
     * @return Ввод пользователя или команда выхода по умолчанию
     */
    private String getUserInput() {
        System.out.println(Constants.SELECT_AN_ACTION);
        return Optional.ofNullable(scanner.next()).orElse(EXIT);
    }

}
