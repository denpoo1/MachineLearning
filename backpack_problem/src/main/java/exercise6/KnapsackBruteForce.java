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
        int n = items.size();
        int bestValue = 0;
        int bestWeight = 0;
        List<Integer> bestCombination = new ArrayList<>();
        long startTime = System.currentTimeMillis();

        // Generate all possible combinations of items
        for (int i = 1; i < (1 << n); i++) {
            int totalWeight = 0;
            int totalValue = 0;
            List<Integer> currentCombination = new ArrayList<>();

            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) != 0) {
                    totalWeight += items.get(j).weight;
                    totalValue += items.get(j).value;
                    currentCombination.add(1);
                } else {
                    currentCombination.add(0);
                }
            }

            if (totalWeight <= capacity && totalValue > bestValue) {
                bestValue = totalValue;
                bestWeight = totalWeight;
                bestCombination = new ArrayList<>(currentCombination);
            }
        }

        long endTime = System.currentTimeMillis();
        double executionTime = (endTime - startTime) / 1000.0;

        // Output the best combination, total weight, total value, and execution time
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