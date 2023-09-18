package assignment;
import java.io.*;
import java.util.*;

public class BranchAndBound {
    static class Treasure {
        String name;
        double weight;
        double value;

        Treasure(String name, double weight, double value) {
            this.name = name;
            this.weight = weight;
            this.value = value;
        }
    }

    public static double knapsackBranchAndBound(List<Treasure> treasures, double[] capacities) {
        double[] maxValues = new double[1];
        maxValues[0] = 0;
        branchAndBound(treasures, capacities, 0, 0, 0, maxValues);
        return maxValues[0];
    }

    private static void branchAndBound(List<Treasure> treasures, double[] capacities, int level, double currentWeight, double currentValue, double[] maxValues) {
        if (level == treasures.size()) {
            maxValues[0] = Math.max(maxValues[0], currentValue);
            return;
        }

        for (int i = 0; i < capacities.length; i++) {
            if (currentWeight + treasures.get(level).weight <= capacities[i]) {
                capacities[i] -= treasures.get(level).weight;
                branchAndBound(treasures, capacities, level + 1, currentWeight + treasures.get(level).weight, currentValue + treasures.get(level).value, maxValues);
                capacities[i] += treasures.get(level).weight;
            }
        }

        branchAndBound(treasures, capacities, level + 1, currentWeight, currentValue, maxValues);
    }

    public static List<Treasure> readTreasures(String filePath) throws IOException {
        List<Treasure> treasures = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\s+");
            String name = String.join(" ", Arrays.copyOfRange(parts, 0, parts.length - 2));
            double weight = Double.parseDouble(parts[parts.length - 2]);
            double value = Double.parseDouble(parts[parts.length - 1]);
            treasures.add(new Treasure(name, weight, value));
        }
        reader.close();
        return treasures;
    }

    public static List<Double> treasureExpedition(List<Treasure> treasures, double[] capacities) {
        List<Double> totalValues = new ArrayList<>();

        for (double capacity : capacities) {
            List<Treasure> knapsack = new ArrayList<>();
            double remainingCapacity = capacity;
            double totalValue = 0.0;

            for (Treasure treasure : treasures) {
                if (treasure.weight <= remainingCapacity) {
                    knapsack.add(treasure);
                    remainingCapacity -= treasure.weight;
                    totalValue += treasure.value;
                }
            }

            System.out.printf("Luggage capacity: %.2fkg, Total value: $%.2f%n", capacity, totalValue);
            for (Treasure item : knapsack) {
                System.out.printf("  - %s: Weight=%.2fkg, Value=$%.2f%n", item.name, item.weight, item.value);
            }

            totalValues.add(totalValue);
            System.out.println();
        }

        return totalValues;
    }

    public static String calculateSpaceComplexity(List<Treasure> treasures, double[] capacities) {
        int n = treasures.size(); // Number of treasures
        int m = capacities.length; // Number of luggage capacities

        // Calculate space complexities in bytes
        long treasuresListSpace = n * (4 * 8); // Assuming 4 references (64 bits) per Treasure object
        long capacitiesArraySpace = m * 8; // Assuming 8 bytes per double (capacity)

        // Calculate the total space complexity in bytes
        long totalSpaceComplexity = treasuresListSpace + capacitiesArraySpace;

        return "Space Complexity: " + totalSpaceComplexity + " bytes";
    }

    public static void main(String[] args) throws IOException {
        String treasuresFile = "src/assignment/treasures.txt";
        List<Treasure> treasures = readTreasures(treasuresFile);

        double[] capacities = {3.0, 5.0, 7.0, 15.0, 20.0};

        String spaceComplexityResult = calculateSpaceComplexity(treasures, capacities);
        System.out.println(spaceComplexityResult);

        List<Double> totalValues = treasureExpedition(treasures, capacities);
        System.out.println("Total values of treasures in the knapsacks: " + totalValues);
    }
}
