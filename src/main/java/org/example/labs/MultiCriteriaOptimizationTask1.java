package org.example.labs;

import java.util.Arrays;

public class MultiCriteriaOptimizationTask1 {
    public static void main(String[] args) {
        System.out.println("Початкові дані про верстати:");
        double[][] machines = {
                {1000, 100, 8},    // Станок 1
                {1500, 90, 7},     // Станок 2
                {2000, 85, 6},     // Станок 3
                {2500, 80, 7.5}    // Станок 4
        };
        printMachines(machines);

        normalizeData(machines);
        System.out.println("\nНормалізовані дані:");
        printMachines(machines);

        double[] weights = {0.4, 0.35, 0.25};
        System.out.println("\nКоефіцієнти важливості: " + Arrays.toString(weights));

        int bestAdditive = bestMachineByAdditive(machines, weights);
        int bestMultiplicative = bestMachineByMultiplicative(machines, weights);
        int bestMaximin = bestMachineByMaximin(machines);
        int bestMinimax = bestMachineByMinimax(machines);

        System.out.println("\n===== Найкращі верстати за різними критеріями =====");
        System.out.println("Найкращий станок за адитивною згорткою: Станок " + (bestAdditive + 1));
        System.out.println("Найкращий станок за мультиплікативною згорткою: Станок " + (bestMultiplicative + 1));
        System.out.println("Найкращий верстат за максимінним критерієм: Станок " + (bestMaximin + 1));
        System.out.println("Найкращий верстат за мінімаксним критерієм: Станок " + (bestMinimax + 1));
    }

    public static void printMachines(double[][] machines) {
        for (int i = 0; i < machines.length; i++) {
            System.out.println("Станок " + (i + 1) + ": " + Arrays.toString(machines[i]));
        }
    }

    public static void normalizeData(double[][] machines) {
        double[] maxValues = new double[machines[0].length];
        double[] minValues = new double[machines[0].length];
        Arrays.fill(minValues, Double.POSITIVE_INFINITY);

        for (double[] machine : machines) {
            for (int j = 0; j < machine.length; j++) {
                maxValues[j] = Math.max(maxValues[j], machine[j]);
                minValues[j] = Math.min(minValues[j], machine[j]);
            }
        }

        for (int i = 0; i < machines.length; i++) {
            for (int j = 0; j < machines[i].length; j++) {
                if (j == 2) {
                    machines[i][j] = minValues[j] / machines[i][j];
                } else {
                    machines[i][j] = machines[i][j] / maxValues[j];
                }
            }
        }

    }

    public static int bestMachineByAdditive(double[][] machines, double[] weights) {
        int bestIndex = -1;
        double bestScore = Double.NEGATIVE_INFINITY;

        System.out.println("\nРозрахунок адитивної згортки:");
        for (int i = 0; i < machines.length; i++) {
            double score = 0;
            for (int j = 0; j < weights.length; j++) {
                score += weights[j] * machines[i][j];
            }
            System.out.printf("Станок %d: адитивна оцінка = %.4f\n", i + 1, score);
            if (score > bestScore) {
                bestScore = score;
                bestIndex = i;
            }
        }
        System.out.println("Найкращий станок за мінімаксним критерієм: Станок " + (bestIndex+1) + " із значенням " + bestScore);
        return bestIndex;
    }

    public static int bestMachineByMultiplicative(double[][] machines, double[] weights) {
        int bestIndex = -1;
        double bestScore = Double.NEGATIVE_INFINITY;

        System.out.println("\nРозрахунок мультиплікативної згортки:");
        for (int i = 0; i < machines.length; i++) {
            double score = 1;
            for (int j = 0; j < weights.length; j++) {
                score *= Math.pow(machines[i][j], weights[j]);
            }
            System.out.printf("Станок %d: мультиплікативна оцінка = %.4f\n", i + 1, score);
            if (score > bestScore) {
                bestScore = score;
                bestIndex = i;
            }
        }
        System.out.println("Найкращий станок за мінімаксним критерієм: Станок " + (bestIndex+1) + " із значенням " + bestScore);
        return bestIndex;
    }

    public static int bestMachineByMaximin(double[][] machines) {
        int bestIndex = -1;
        double bestValue = Double.NEGATIVE_INFINITY;

        System.out.println("\nРозрахунок максимінного критерію:");
        for (int i = 0; i < machines.length; i++) {
            double minValue = Arrays.stream(machines[i]).min().orElse(Double.NEGATIVE_INFINITY);
            System.out.printf("Станок %d: мінімальне значення = %.4f\n", i + 1, minValue);
            if (minValue > bestValue) {
                bestValue = minValue;
                bestIndex = i;
            }
        }
        System.out.println("Найкращий станок за максимінним критерієм: Станок " + (bestIndex+1 ) + " із значенням " + bestValue);
        return bestIndex;
    }

    public static int bestMachineByMinimax(double[][] machines) {
        int bestIndex = -1;
        double bestValue = Double.POSITIVE_INFINITY;

        System.out.println("\nРозрахунок мінімаксного критерію:");
        for (int i = 0; i < machines.length; i++) {
            double maxValue = Arrays.stream(machines[i]).max().orElse(Double.POSITIVE_INFINITY);
            System.out.printf("Станок %d: максимальне значення = %.4f\n", i + 1, maxValue);
            if (maxValue < bestValue) {
                bestValue = maxValue;
                bestIndex = i;
            }
        }
        System.out.println("Найкращий станок за мінімаксним критерієм: Станок " + (bestIndex + 1) + " із значенням " + bestValue);
        return bestIndex;
    }
}
