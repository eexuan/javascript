//import necessary library
package assignment;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); //to get user input
        boolean exitRequested = false;

        do {
            printMenu();

            int choice = 0; // Initialize choice to an invalid value

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); 
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid numeric option (0-3).");
                scanner.nextLine(); 
                continue; // Skip the rest of the loop and prompt again
            }

            switch (choice) {
                case 1:
                    System.out.println("\nRunning Brute Force Algorithm:");
                    runBruteForceAlgorithm();
                    break;

                case 2:
                    System.out.println("\nRunning Greedy Algorithm:");
                    runGreedyAlgorithm();
                    break;

                case 3:
                    System.out.println("\nRunning Branch and Bound Algorithm:");
                    runBranchAndBoundAlgorithm();
                    break;

                case 0:
                    System.out.println("Exiting the program.");
                    exitRequested = true;
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a valid option (0-3).");
            }

            if (!exitRequested) {
                int continueChoice;

                do {
                    System.out.print("\nEnter 0 to exit or 1 to return to the menu: ");

                    try {
                        continueChoice = scanner.nextInt();
                        scanner.nextLine(); 
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter 0 to exit or 1 to return to the menu.");
                        scanner.nextLine(); 
                        continue; 
                    }

                    if (continueChoice == 0) {
                        System.out.println("Exiting the program.");
                        exitRequested = true;
                        break; // Exit the loop
                    } else if (continueChoice == 1) {
                        break; // Return to the menu
                    } else {
                        System.out.println("Invalid choice. Please enter 0 to exit or 1 to return to the menu.");
                    }
                } while (true);
            }
        } while (!exitRequested);

        scanner.close();
    }

    public static void runBruteForceAlgorithm() {
        try {
            BruteForce.main(new String[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void runGreedyAlgorithm() {
        try {
            GreedyAlgorithm.main(new String[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void runBranchAndBoundAlgorithm() {
        try {
            BranchAndBound.main(new String[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printMenu() {
        System.out.println("=============================================");
        System.out.println("***** Welcome to Treasure Expedition *****");
        System.out.println("=============================================");
        System.out.println("Choose an algorithm to optimize your treasure");
        System.out.println("expedition and maximize the total value of");
        System.out.println("treasures while minimizing the number of items carried:");
        System.out.println("---------------------------------------------");
        System.out.println("1. Brute Force Algorithm");
        System.out.println("2. Greedy Algorithm");
        System.out.println("3. Branch and Bound Algorithm");
        System.out.println("0. Exit");
        System.out.println("---------------------------------------------");
        System.out.print("Enter the number of your choice: ");
    }
}