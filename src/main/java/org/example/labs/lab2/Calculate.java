package org.example.labs.lab2;

import java.util.*;

public class Calculate {

    public static void main(String[] args) {
        double[] weights = {0.1, 0.5, 0.4};
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
                new Machine(10, 2000, 65, 7.1),
                new Machine(11, 2100, 61, 7.4),
                new Machine(12, 2200, 60, 7.0),
                new Machine(13, 2300, 58, 6.8),
                new Machine(14, 2400, 55, 5.0),
                new Machine(15, 2500, 50, 6.5),
                new Machine(16, 2600, 70, 7.0),
                new Machine(17, 2100, 80, 8.0),
                new Machine(18, 2000, 75, 9.0)
        );

        System.out.println("Всі машини з розрахованими зваженими сумами:");
        for (Machine machine : machines) {
            System.out.printf("%s | Зважена сума: %.2f\n", machine, machine.weightedSum(weights));
        }

        List<Machine> paretoFront = findParetoOptimalMachines(machines, weights);
        System.out.println("\nПарето-оптимальні машини:");
        for (Machine machine : paretoFront) {
            System.out.printf("%s | Зважена сума: %.2f\n", machine, machine.weightedSum(weights));
        }

        if (paretoFront.size() > 4) {
            paretoFront.sort(Comparator.comparingDouble(m -> -m.weightedSum(weights)));
            List<Machine> topParetoMachines = paretoFront.subList(0, Math.min(4, paretoFront.size()));
            System.out.println("\nТоп-4 Парето-оптимальних машини:");
            for (Machine machine : topParetoMachines) {
                System.out.printf("%s | Зважена сума: %.2f\n", machine, machine.weightedSum(weights));
            }
        }
    }

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

    private static boolean dominates(Machine machine1, Machine machine2, double[] weights) {
        boolean betterInAtLeastOne = false;
        for (int i = 0; i < machine1.criteria.length; i++) {
            double weighted1 = machine1.criteria[i] * weights[i];
            double weighted2 = machine2.criteria[i] * weights[i];
            if (i == 2) {
                if (weighted1 > weighted2) return false;
                if (weighted1 < weighted2) betterInAtLeastOne = true;
            } else {
                if (weighted1 < weighted2) return false;
                if (weighted1 > weighted2) betterInAtLeastOne = true;
            }
        }
        return betterInAtLeastOne;
    }
}
