//import necessary library
package assignment;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BruteForce {

    public static List<Treasure1> readTreasuresFromFile(String filename) throws IOException {
        List<Treasure1> treasures = new ArrayList<>();
        //read file
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                StringBuilder nameBuilder = new StringBuilder();
                for (int i = 0; i < parts.length - 2; i++) {
                    nameBuilder.append(parts[i]);
                    if (i < parts.length - 3) {
                        nameBuilder.append(" ");
                    }
                }
                String name = nameBuilder.toString();
                double weight = Double.parseDouble(parts[parts.length - 2]);
                double value = Double.parseDouble(parts[parts.length - 1]);
                treasures.add(new Treasure1(name, weight, value));
            }
        }

        return treasures;
    }
    //algorithm
    public static Result bruteForceTreasureExpedition(List<Treasure1> treasures, double luggageCapacity) {
        int n = treasures.size();
        double maxValue = 0;
        List<Treasure1> bestCombination = new ArrayList<>();

        for (int i = 0; i < (1 << n); i++) {
            List<Treasure1> currentCombination = new ArrayList<>();
            double totalWeight = 0;
            double totalValue = 0;

            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) != 0) {
                    Treasure1 treasure = treasures.get(j);
                    currentCombination.add(treasure);
                    totalWeight += treasure.getWeight();
                    totalValue += treasure.getValue();
                }
            }

            if (totalWeight <= luggageCapacity && totalValue > maxValue) {
                maxValue = totalValue;
                bestCombination = currentCombination;
            }
        }

        return new Result(maxValue, bestCombination);
    }

    public static String calculateSpaceComplexity(List<Treasure1> treasures, double luggageCapacity) {
        int n = treasures.size(); // Number of treasures
        int maxCombinationSize = 0;

        for (int i = 0; i < (1 << n); i++) {
            List<Treasure1> currentCombination = new ArrayList<>();

            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) != 0) {
                    Treasure1 treasure = treasures.get(j);
                    currentCombination.add(treasure);
                }
            }

            maxCombinationSize = Math.max(maxCombinationSize, currentCombination.size());
        }

        // Estimate the space used by the algorithm
        // Assuming each Treasure1 object takes 8 bytes for simplicity
        long treasuresListSpace = n * 8;
        long currentCombinationSpace = maxCombinationSize * 8; // Assuming 8 bytes per Treasure1 object reference
        long otherVariablesSpace = 5 * 8; // Assuming 5 variables each taking 8 bytes

        // Calculate the total space complexity in bytes
        long totalSpaceComplexity = treasuresListSpace + currentCombinationSpace + otherVariablesSpace;

        return "Space Complexity: " + totalSpaceComplexity + " bytes";
    }

    public static void main(String[] args) throws IOException {
        String treasuresFile = "src/assignment/treasures.txt";
        double[] luggageCapacities = { 3, 5, 7, 15, 20 };
        List<Treasure1> treasures = readTreasuresFromFile(treasuresFile);
        
        // Calculate and print the space complexity
        String spaceComplexityResult = calculateSpaceComplexity(treasures, luggageCapacities[0]); // Assuming the same for all capacities
        System.out.println("Space Complexity: " + spaceComplexityResult);

        for (double capacity : luggageCapacities) {
            Result result = bruteForceTreasureExpedition(treasures, capacity);
            System.out.printf("Luggage capacity: %.2fkg, Total value: %.2f\n", capacity, result.getMaxValue());
            System.out.println("Selected treasures:");
            for (Treasure1 item : result.getBestCombination()) {
                System.out.printf("  - %s: Weight=%.2fkg, Value=$%.2f\n", item.getName(), item.getWeight(), item.getValue());
            }
        }
        
        // Calculate and print the total values
        List<Double> totalValues = new ArrayList<>();
        for (double capacity : luggageCapacities) {
            Result result = bruteForceTreasureExpedition(treasures, capacity);
            totalValues.add(result.getMaxValue());
        }
        System.out.println(" ");
        System.out.println("Total values of treasures in the knapsacks: " + totalValues);
    }
}

class Treasure1 {
    private String name;
    private double weight;
    private double value;

    public Treasure1(String name, double weight, double value) {
        this.name = name;
        this.weight = weight;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public double getValue() {
        return value;
    }
}

class Result {
    private double maxValue;
    private List<Treasure1> bestCombination;

    public Result(double maxValue, List<Treasure1> bestCombination) {
        this.maxValue = maxValue;
        this.bestCombination = bestCombination;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public List<Treasure1> getBestCombination() {
        return bestCombination;
    }
}