package org.example.labs.lab1;

import java.util.Arrays;

public class MultiCriteriaOptimizationTask2 {
    public static void main(String[] args) {
        System.out.println("Початкові дані про верстати:");
        double[][] machines = {
                {1000, 100, 8},    // Станок 1
                {1500, 90, 7},     // Станок 2
                {2000, 85, 6},     // Станок 3
                {2500, 80, 7.5}    // Станок 4
        };
        MultiCriteriaOptimizationTask1.printMachines(machines);

        MultiCriteriaOptimizationTask1.normalizeData(machines);
        System.out.println("\nНормалізовані дані:");
        MultiCriteriaOptimizationTask1.printMachines(machines);

        int[][] expertRatings = {
                {8, 7, 5},
                {9, 6, 4},
                {7, 8, 5},
                {8, 7, 6}
        };

        double[] weights = calculateWeightsByScoring(expertRatings);
        System.out.println("\nКоефіцієнти важливості: " + Arrays.toString(weights));

        int bestAdditive = MultiCriteriaOptimizationTask1.bestMachineByAdditive(machines, weights);
        int bestMultiplicative = MultiCriteriaOptimizationTask1.bestMachineByMultiplicative(machines, weights);
        int bestMaximin = bestMachineByMaximin(machines);
        int bestMinimax = bestMachineByMinimax(machines);

        System.out.println("\n===== Найкращі верстати за різними критеріями =====");
        System.out.println("Найкращий станок за адитивною згорткою: Станок " + (bestAdditive ));
        System.out.println("Найкращий станок за мультиплікативною згорткою: Станок " + (bestMultiplicative ));
        System.out.println("Найкращий верстат за максимінним критерієм: Станок " + (bestMaximin + 1));
        System.out.println("Найкращий верстат за мінімаксним критерієм: Станок " + (bestMinimax + 1));
    }

    private static double[] calculateWeightsByScoring(int[][] ratings) {
        int numCriteria = ratings[0].length;
        double[] weights = new double[numCriteria];
        int[] sumRatings = new int[numCriteria];

        System.out.println("\nРозрахунок коефіцієнтів важливості:");
        System.out.println("Кожен експерт оцінює важливість критеріїв за шкалою (1-10).");

        for (int i = 0; i < ratings.length; i++) {
            System.out.println("Оцінки експерта " + (i + 1) + ": " + Arrays.toString(ratings[i]));
        }

        for (int[] expert : ratings) {
            for (int j = 0; j < numCriteria; j++) {
                sumRatings[j] += expert[j];
            }
        }

        System.out.println("\nСума балів по критеріях: " + Arrays.toString(sumRatings));
        int totalSum = Arrays.stream(sumRatings).sum();
        System.out.println("Загальна сума всіх оцінок: " + totalSum);

        for (int j = 0; j < numCriteria; j++) {
            weights[j] = (double) sumRatings[j] / totalSum;
            System.out.printf("Вага критерію %d: %." +
                    "4f ( %d / %d )\n", j + 1, weights[j], sumRatings[j], totalSum);
        }
        return weights;
    }

    private static int bestMachineByMaximin(double[][] machines) {
        int bestIndex = -1;
        double bestValue = Double.NEGATIVE_INFINITY;

        System.out.println("\nРозрахунок максимінного критерію:");
        for (int i = 0; i < machines.length; i++) {
            double minValue = Arrays.stream(machines[i]).min().orElse(Double.NEGATIVE_INFINITY);
            System.out.println("Станок " + (i + 1) + ": мінімальне значення = " + minValue);

            if (minValue > bestValue) {
                bestValue = minValue;
                bestIndex = i;
            }
        }
        System.out.println("Найкращий станок за максимінним критерієм: Станок " + (bestIndex + 1) + " із значенням " + bestValue);
        return bestIndex;
    }

    private static int bestMachineByMinimax(double[][] machines) {
        int bestIndex = -1;
        double bestValue = Double.POSITIVE_INFINITY;

        System.out.println("\nРозрахунок мінімаксного критерію:");
        for (int i = 0; i < machines.length; i++) {
            double maxValue = Arrays.stream(machines[i]).max().orElse(Double.POSITIVE_INFINITY);
            System.out.println("Станок " + (i + 1) + ": максимальне значення = " + maxValue);

            if (maxValue < bestValue) {
                bestValue = maxValue;
                bestIndex = i;
            }
        }
        System.out.println("Найкращий станок за мінімаксним критерієм: Станок " + (bestIndex + 1) + " із значенням " + bestValue);
        return bestIndex;
    }
}
