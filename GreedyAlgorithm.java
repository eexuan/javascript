//import necessary library
package assignment;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

class Treasure {
    String name;
    double weight;
    double value;
    double valueToWeightRatio;

    Treasure(String name, double weight, double value) {
        this.name = name;
        this.weight = weight;
        this.value = value;
        this.valueToWeightRatio = value / weight;
    }
}

public class GreedyAlgorithm {
    public static List<Treasure> readTreasures(String filePath) throws IOException {
        List<Treasure> treasures = new ArrayList<>();
        //ready file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                String name = String.join(" ", Arrays.copyOfRange(parts, 0, parts.length - 2));
                double weight = Double.parseDouble(parts[parts.length - 2]);
                double value = Double.parseDouble(parts[parts.length - 1]);
                treasures.add(new Treasure(name, weight, value));
            }
        }
        return treasures;
    }

    public static List<Double> treasureExpedition(List<Treasure> treasures, int[] luggageCapacities) {
        List<Double> totalValues = new ArrayList<>();
        for (int capacity : luggageCapacities) {
            List<Treasure> knapsack = new ArrayList<>();
            double remainingCapacity = capacity;

            treasures.sort(Comparator.comparingDouble(treasure -> -treasure.valueToWeightRatio));

            for (Treasure treasure : treasures) {
                if (treasure.weight <= remainingCapacity) {
                    knapsack.add(treasure);
                    remainingCapacity -= treasure.weight;
                }
            }

            double totalValue = knapsack.stream().mapToDouble(treasure -> treasure.value).sum();
            totalValues.add(totalValue);

            System.out.println("Luggage capacity: " + capacity + "kg, Total value: $" + totalValue);
            for (Treasure item : knapsack) {
                System.out.println("  - " + item.name + ": Weight=" + item.weight + "kg, Value=$" + item.value);
            }
        }
        return totalValues;
    }
    
    public static String calculateSpaceComplexityInBytes(List<Treasure> treasures, int[] luggageCapacities) {
        int n = treasures.size(); // Number of treasures
        int m = luggageCapacities.length; // Number of luggage capacities

        // Calculate space complexities in bytes
        long treasuresListSpace = n * (4 * 8); // Assuming 4 references (64 bits) per Treasure object
        long totalValuesListSpace = m * 8; // Assuming 8 bytes per double (totalValue)
        long sortingSpace = n * (4 * 8); // Space for sorting operation

        // Calculate the maximum space used by knapsack in any iteration
        long maxKnapsackSpace = 0;
        for (int capacity : luggageCapacities) {
            List<Treasure> knapsack = new ArrayList<>();
            double remainingCapacity = capacity;

            for (Treasure treasure : treasures) {
                if (treasure.weight <= remainingCapacity) {
                    knapsack.add(treasure);
                    remainingCapacity -= treasure.weight;
                }
            }

            long knapsackSpace = knapsack.size() * (4 * 8); // Assuming 4 references (64 bits) per Treasure object
            maxKnapsackSpace = Math.max(maxKnapsackSpace, knapsackSpace);
        }

        // Calculate the total space complexity in bytes
        long totalSpaceComplexity = treasuresListSpace + totalValuesListSpace + sortingSpace + maxKnapsackSpace;

        return "Space Complexity: " + totalSpaceComplexity + " bytes";
    }


    public static void main(String[] args) throws IOException {
        String treasuresFile = "src/assignment/treasures.txt";
        int[] luggageCapacities = {3, 5, 7, 15, 20};

        List<Treasure> treasures = readTreasures(treasuresFile);
        
        String spaceComplexityResult = calculateSpaceComplexityInBytes(treasures, luggageCapacities);

        System.out.println(spaceComplexityResult);

        List<Double> totalValues = treasureExpedition(treasures, luggageCapacities);
        System.out.println(" ");
        System.out.println("Total values of treasures in the knapsacks: " + totalValues);
    }
}
