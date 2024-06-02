package de.buw.se;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.util.*;

public class TransportRouteFinder {

     public static void run() {
        // Prompt the users to enter starting location and destination
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter starting location: ");
        String startingLocation = scanner.nextLine();
        System.out.print("Enter destination: ");
        String destination = scanner.nextLine();
        scanner.close();

        // Reading the CSV file
        File file = new File("src/main/resources/TransportRouteFinder.csv");
        if (!file.exists()) {
            System.out.println("CSV file not found.");
            return;
        }

        // Searching for the routes in the CSV file
        boolean routeFound = false;
        String intermediateStop = "";
        boolean startToInterAvailable   = false;
        boolean interToDestAvailable = false;
        String availableModesStartToInter = "";
        String availableModesInterToDest = "";
        double minCarbonEmissionStartToInter = Double.MAX_VALUE;
        String leastCarbonModeStartToInter = "";
        double minCarbonEmissionInterToDest = Double.MAX_VALUE;
        String leastCarbonModeInterToDest = "";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            // Skip the header row
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String start = parts[0];
                String end = parts[1];
                intermediateStop = parts[2];
                boolean carAvailableStartToInter = Boolean.parseBoolean(parts[3]);
                boolean busAvailableStartToInter = Boolean.parseBoolean(parts[4]);
                boolean trainAvailableStartToInter = Boolean.parseBoolean(parts[5]);
                boolean bikeAvailableStartToInter = Boolean.parseBoolean(parts[6]);
                boolean carAvailableInterToDest = Boolean.parseBoolean(parts[11]);
                boolean busAvailableInterToDest = Boolean.parseBoolean(parts[12]);
                boolean trainAvailableInterToDest = Boolean.parseBoolean(parts[13]);
                boolean bikeAvailableInterToDest = Boolean.parseBoolean(parts[14]);
                double carDistanceStartToInter = Double.parseDouble(parts[7]);
                double busDistanceStartToInter = Double.parseDouble(parts[8]);
                double trainDistanceStartToInter = Double.parseDouble(parts[9]);
                double bikeDistanceStartToInter = Double.parseDouble(parts[10]);
                double carDistanceInterToDest = Double.parseDouble(parts[15]);
                double busDistanceInterToDest = Double.parseDouble(parts[16]);
                double trainDistanceInterToDest = Double.parseDouble(parts[17]);
                double bikeDistanceInterToDest = Double.parseDouble(parts[18]);

                if (start.equalsIgnoreCase(startingLocation) && end.equalsIgnoreCase(destination)) {
                    routeFound = true;
                    startToInterAvailable = true;
                    interToDestAvailable = true;

                    // Calculate carbon emission for the mode of transport available from starting location to intermediate stop
                    if (carAvailableStartToInter) {
                        double carCarbonEmission = carDistanceStartToInter * 0.2; // Let 0.2 kg CO2 per km for car
                        availableModesStartToInter += "Car (Carbon Emission: " + carCarbonEmission + " kg), ";
                        if (carCarbonEmission < minCarbonEmissionStartToInter) {
                            minCarbonEmissionStartToInter = carCarbonEmission;
                            leastCarbonModeStartToInter = "Car";
                        }
                    }
                    if (busAvailableStartToInter) {
                        double busCarbonEmission = busDistanceStartToInter * 0.1; // Let 0.1 kg CO2 per km for bus
                        availableModesStartToInter += "Bus (Carbon Emission: " + busCarbonEmission + " kg), ";
                        if (busCarbonEmission < minCarbonEmissionStartToInter) {
                            minCarbonEmissionStartToInter = busCarbonEmission;
                            leastCarbonModeStartToInter = "Bus";
                        }
                    }
                    if (trainAvailableStartToInter) {
                        double trainCarbonEmission = trainDistanceStartToInter * 0.05; // Let 0.05 kg CO2 per km for train
                        availableModesStartToInter += "Train (Carbon Emission: " + trainCarbonEmission + " kg), ";
                        if (trainCarbonEmission < minCarbonEmissionStartToInter) {
                            minCarbonEmissionStartToInter = trainCarbonEmission;
                            leastCarbonModeStartToInter = "Train";
                        }
                    }
                    if (bikeAvailableStartToInter) {
                        double bikeCarbonEmission = bikeDistanceStartToInter * 0.01; // Let 0.01 kg CO2 per km for bike
                        availableModesStartToInter += "Bike (Carbon Emission: " + bikeCarbonEmission + " kg), ";
                        if (bikeCarbonEmission < minCarbonEmissionStartToInter) {
                            minCarbonEmissionStartToInter = bikeCarbonEmission;
                            leastCarbonModeStartToInter = "Bike";
                        }
                    }

                    // Calculate carbon emission for the mode of transport available from intermediate stop to destination
                    if (carAvailableInterToDest) {
                        double carCarbonEmission = carDistanceInterToDest * 0.2; // Let 0.2 kg CO2 per km for car
                        availableModesInterToDest += "Car (Carbon Emission: " + carCarbonEmission + " kg), ";
                        if (carCarbonEmission < minCarbonEmissionInterToDest) {
                            minCarbonEmissionInterToDest = carCarbonEmission;
                            leastCarbonModeInterToDest = "Car";
                        }
                    }
                    if (busAvailableInterToDest) {
                        double busCarbonEmission = busDistanceInterToDest * 0.1; // Let 0.1 kg CO2 per km for bus
                        availableModesInterToDest += "Bus (Carbon Emission: " + busCarbonEmission + " kg), ";
                        if (busCarbonEmission < minCarbonEmissionInterToDest) {
                            minCarbonEmissionInterToDest = busCarbonEmission;
                            leastCarbonModeInterToDest = "Bus";
                        }
                    }
                    if (trainAvailableInterToDest) {
                        double trainCarbonEmission = trainDistanceInterToDest * 0.05; // let 0.05 kg CO2 per km for train
                        availableModesInterToDest += "Train (Carbon Emission: " + trainCarbonEmission + " kg), ";
                        if (trainCarbonEmission < minCarbonEmissionInterToDest) {
                            minCarbonEmissionInterToDest = trainCarbonEmission;
                            leastCarbonModeInterToDest = "Train";
                        }
                    }
                    if (bikeAvailableInterToDest) {
                        double bikeCarbonEmission = bikeDistanceInterToDest * 0.01; // Let 0.01 kg CO2 per km for bike
                        availableModesInterToDest += "Bike (Carbon Emission: " + bikeCarbonEmission + " kg), ";
                        if (bikeCarbonEmission < minCarbonEmissionInterToDest) {
                            minCarbonEmissionInterToDest = bikeCarbonEmission;
                            leastCarbonModeInterToDest = "Bike";
                        }
                    }
                    break;
                }
            }
            if (!routeFound) {
                System.out.println("Route not found for the given locations.");
            } else {
                System.out.println("Intermediate Stop: " + intermediateStop);
                System.out.println("Route: " + startingLocation +"---->" + intermediateStop +"---->" + destination);
                System.out.println("Available modes of transport from " + startingLocation +"---->" + intermediateStop  + ":::" + availableModesStartToInter);
                System.out.println("Least carbon emission mode of transport from " + startingLocation + " to " +   intermediateStop  + "---->" + leastCarbonModeStartToInter);
                System.out.println("Available modes of transport from " + intermediateStop + " to " + destination + ": " + availableModesInterToDest);
                System.out.println("Least carbon emission mode of transport from "+ intermediateStop + " to "  + destination + ": " + leastCarbonModeInterToDest);
                System.out.println("Least carbon emitted mode of transport from " + startingLocation +"-->" + destination +"(via:" + intermediateStop + ")" );
                System.out.println("************************* :" + leastCarbonModeStartToInter + "------>" + leastCarbonModeInterToDest);
                System.out.println("Total carbon emission:" + ((+ minCarbonEmissionStartToInter ) + ( + minCarbonEmissionInterToDest) ) + "kg" );
                
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
    }
}

