package exercise6;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class implements a brute force solution to the knapsack problem.
 */
public class KnapsackBruteForce {

    /**
     * This method generates all possible subsets of items and finds the best one.
     * The best subset is the one that has the maximum total value and its total weight does not exceed the capacity.
     *
     * @param capacity The maximum weight that the knapsack can hold.
     * @param items The list of items, where each item has a weight and a value.
     */
    public static void knapsackBruteForce(int capacity, List<Item> items) {
        int n = items.size(); // Количество предметов
        int bestValue = 0; // Лучшая найденная ценность
        int bestWeight = 0; // Вес лучшего найденного набора предметов
        List<Integer> bestCombination = new ArrayList<>(); // Лучшая комбинация предметов
        long startTime = System.currentTimeMillis(); // Время начала выполнения

        // Генерация всех возможных комбинаций предметов
        /**
         * 1 << n это 2^n, так как каждый предмет может быть включен или не включен в комбинацию.
         * Поэтому всего возможно 2^n комбинаций.
         * Например, если n = 3, то 1 << 3 = 8, что соответствует 2^3 = 8 комбинациям.
         */
        for (int i = 1; i < (1 << n); i++) { // Проход по всем подмножествам (1 << n это 2^n)
            int totalWeight = 0; // Текущий суммарный вес
            int totalValue = 0; // Текущая суммарная ценность
            List<Integer> currentCombination = new ArrayList<>(); // Текущая комбинация

            for (int j = 0; j < n; j++) { // Проход по каждому предмету
                /**
                 * Например i = 5 (101 в двоичной системе) и j = 0, 1, 2.
                 * Пример 1: i = 5, j = 0, j = 1, j = 2
                 * Значения:
                 *
                 *     i = 5 (в двоичном виде 101)
                 *
                 * Проверка:
                 *
                 *     j = 0
                 *         1 << 0 = 001 (в двоичном виде)
                 *         i & (1 << 0) = 101 & 001 = 001 (не равно 0)
                 *         Бит на позиции 0 установлен, значит, первый предмет включен.
                 *
                 *     j = 1
                 *         1 << 1 = 010 (в двоичном виде)
                 *         i & (1 << 1) = 101 & 010 = 000 (равно 0)
                 *         Бит на позиции 1 не установлен, значит, второй предмет не включен.
                 *
                 *     j = 2
                 *         1 << 2 = 100 (в двоичном виде)
                 *         i & (1 << 2) = 101 & 100 = 100 (не равно 0)
                 *         Бит на позиции 2 установлен, значит, третий предмет включен.
                 *
                 * Пример 2: i = 3, j = 0, j = 1, j = 2
                 * Значения:
                 *
                 *     i = 3 (в двоичном виде 011)
                 *
                 * Проверка:
                 *
                 *     j = 0
                 *         1 << 0 = 001 (в двоичном виде)
                 *         i & (1 << 0) = 011 & 001 = 001 (не равно 0)
                 *         Бит на позиции 0 установлен, значит, первый предмет включен.
                 *
                 *     j = 1
                 *         1 << 1 = 010 (в двоичном виде)
                 *         i & (1 << 1) = 011 & 010 = 010 (не равно 0)
                 *         Бит на позиции 1 установлен, значит, второй предмет включен.
                 *
                 *     j = 2
                 *         1 << 2 = 100 (в двоичном виде)
                 *         i & (1 << 2) = 011 & 100 = 000 (равно 0)
                 *         Бит на позиции 2 не установлен, значит, третий предмет не включен.
                 */
                if ((i & (1 << j)) != 0) { // Проверка, включен ли предмет в текущую комбинацию
                    totalWeight += items.get(j).weight; // Добавление веса предмета
                    totalValue += items.get(j).value; // Добавление ценности предмета
                    currentCombination.add(1); // Предмет включен в комбинацию
                } else {
                    currentCombination.add(0); // Предмет не включен в комбинацию
                }
            }

            // Обновление лучшего набора предметов, если текущий лучше
            if (totalWeight <= capacity && totalValue > bestValue) {
                bestValue = totalValue; // Обновление лучшей ценности
                bestWeight = totalWeight; // Обновление лучшего веса
                bestCombination = new ArrayList<>(currentCombination); // Обновление лучшей комбинации
            }
        }

        long endTime = System.currentTimeMillis(); // Время окончания выполнения
        double executionTime = (endTime - startTime) / 1000.0; // Расчет времени выполнения

        // Вывод результатов
        System.out.println("Best characteristic vector: " + bestCombination);
        System.out.println("Total weight: " + bestWeight);
        System.out.println("Total value: " + bestValue);
        System.out.println("Execution time: " + executionTime + " seconds");
    }

    /**
     * The main method reads the knapsack data from a file, and then calls the knapsackBruteForce method.
     * The file should be located in the "knapsack_data" directory and its name should be "1".
     * The file should contain the capacity of the knapsack and the weight and value of each item.
     *
     * @param args The command line arguments. This parameter is not used in this method.
     */
    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(new File(System.getProperty("user.dir") + "/backpack_problem/src/main/resources/knapsack_data/1"))) {
            int capacity = scanner.nextInt();
            List<Item> items = new ArrayList<>();
            while (scanner.hasNextInt()) {
                int weight = scanner.nextInt();
                int value = scanner.nextInt();
                items.add(new Item(weight, value));
            }

            knapsackBruteForce(capacity, items);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}