package org.example.labs.lab2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Calculate {
    public static void main(String[] args) {
        // Ваги для кожного з трьох критеріїв згідно з варіантом №5
        double[] weights = {0.1, 0.5, 0.4};

        // Список машин з трьома критеріями та їх порядковим номером
        List<Machine> machines = Arrays.asList(
                new Machine(1, 1000, 100, 10.0),
                new Machine(2, 1100, 110, 9.0),
                new Machine(3, 1200, 95, 9.5),
                new Machine(4, 1300, 89, 8.0),
                new Machine(5, 1400, 79, 8.4),
                new Machine(6, 1650, 80, 9.0),
                new Machine(7, 1700, 75, 7.8),
                new Machine(8, 1850, 78, 6.5),
                new Machine(9, 1900, 70, 7.5),
                new Machine(10, 2100, 65, 7.1),
                new Machine(11, 2200, 61, 7.4),
                new Machine(12, 2300, 60, 7.0),
                new Machine(13, 2400, 58, 6.8),
                new Machine(14, 2500, 55, 5.0),
                new Machine(15, 2600, 50, 6.5),
                new Machine(16, 2700, 70, 7.0),
                new Machine(17, 2800, 80, 8.0),
                new Machine(18, 2000, 75, 9.0)
        );

        // Знаходимо Парето-оптимальні машини
        List<Machine> paretoFront = findParetoOptimalMachines(machines, weights);

        System.out.println("\n========================");
        System.out.println("Парето-оптимальні станки");
        System.out.println("========================\n");

        if (paretoFront.isEmpty()) {
            System.out.println("Парето-оптимальні станки  не знайдено.");
        } else {
            System.out.println("Список Парето-оптимальних стакни:");
            for (Machine machine : paretoFront) {
                System.out.println(machine);
            }
        }

        if (paretoFront.size() > 4) {
            // Сортуємо Парето-оптимальні машини за зваженою сумою критеріїв у порядку спадання
            paretoFront.sort(Comparator.comparingDouble(m -> -m.weightedSum(weights)));

            // Вибираємо лише перші 4 машини
            List<Machine> topParetoMachines = paretoFront.subList(0, Math.min(4, paretoFront.size()));

            // Виводимо результат
            System.out.println("\n===============================");
            System.out.println("Топ-4 Парето-оптимальних станки ");
            System.out.println("=================================\n");

            for (Machine machine : topParetoMachines) {
                System.out.println(machine);
            }
        }
    }

    // Функція для знаходження Парето-оптимальних машин з урахуванням ваг
    public static List<Machine> findParetoOptimalMachines(List<Machine> machines, double[] weights) {
        List<Machine> paretoFront = new ArrayList<>();

        for (Machine machine1 : machines) {
            boolean isParetoOptimal = true;
            for (Machine machine2 : machines) {
                if (dominates(machine2, machine1, weights)) {
                    isParetoOptimal = false;
                    break;
                }
            }
            if (isParetoOptimal) {
                paretoFront.add(machine1);
            }
        }

        return paretoFront;
    }

    // Функція для перевірки, чи домінує машина 2 над машиною 1 з урахуванням ваг
    private static boolean dominates(Machine machine1, Machine machine2, double[] weights) {
        boolean betterInAtLeastOne = false;
        for (int i = 0; i < machine1.criteria.length; i++) {
            double weighted1 = machine1.criteria[i] * weights[i];  // Зважена оцінка для критеріїв машини 1
            double weighted2 = machine2.criteria[i] * weights[i];  // Зважена оцінка для критеріїв машини 2

            // Якщо порівнюємо третій критерій (вартість), мінімізуємо його
            if (i == 2) {
                // Мінімізуємо вартість, тому якщо ціна машини 1 більша за ціну машини 2, машина 2 не домінує
                if (weighted1 > weighted2) {
                    return false; // Якщо третій критерій гірший у machine1, machine2 домінує
                }
                if (weighted1 < weighted2) {
                    betterInAtLeastOne = true; // Якщо третій критерій кращий у machine2
                }
            } else {
                // Для перших двох критеріїв (надійність і потужність) максимізуємо їх
                if (weighted1 < weighted2) {
                    return false; // Якщо хоча б один з перших двох критеріїв гірший у machine1, machine2 домінує
                }
                if (weighted1 > weighted2) {
                    betterInAtLeastOne = true; // Якщо хоча б один з перших двох критеріїв кращий у machine1
                }
            }
        }
        return betterInAtLeastOne; // Якщо хоча б один критерій у machine1 кращий після зважування
    }
}