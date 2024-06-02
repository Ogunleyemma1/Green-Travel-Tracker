package de.buw.se;
import java.util.Scanner;

public class CarbonFootPrintCalculator {
    public static void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Carbon Emission Calculator!");

        // Input validation for distance
        double distance = ValidateDistance(scanner);

        // Input validation for mode of transport
        int transportChoice = ValidateTransportChoice(scanner);

        double emission = calculateEmission(distance, transportChoice);

        System.out.println("Carbon emission for the journey: " + emission + " kgCO2e");

        scanner.close();
    }

    private static double ValidateDistance(Scanner scanner) {
        double distance;
        do {
            System.out.print("Enter the distance traveled (in kilometers): ");
            while (!scanner.hasNextDouble()) {
                System.out.println("Invalid input! Please enter a valid distance.");
                scanner.next(); // Discard invalid input entered
            }
            distance = scanner.nextDouble();
        } while (distance <= 0); // Ensure distance is positive(valid)
        return distance;
    }

    private static int ValidateTransportChoice(Scanner scanner) {
        int transportChoice;
        do {
            System.out.println("Choose mode of transport:");
            System.out.println("1. Car");
            System.out.println("2. Bike");
            System.out.println("3. Train");
            System.out.println("4. Bus");
            System.out.print("Enter your choice (1-4): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input!!!! Please enter a valid choice (1-4).");
                scanner.next(); // Discard the invalid input entered
            }
            transportChoice = scanner.nextInt();
        } while (transportChoice < 1 || transportChoice > 4); // Ensuring choice is within limit
        return transportChoice;
    }

    private static double calculateEmission(double distance, int choice) {
        double emissionFactor = 0;

        switch (choice) {
            case 1:
                emissionFactor = 0.2; // kgCO2e/km for car
                break;
            case 2:
                emissionFactor = 0.01; // kgCO2e/km for bike
                break;
            case 3:
                emissionFactor = 0.03; // kgCO2e/km for train
                break;
            case 4:
                emissionFactor = 0.05; // kgCO2e/km for bus
                break;
            default:
                System.out.println("Invalid choice!");
        }

        return distance * emissionFactor;
    }
}


