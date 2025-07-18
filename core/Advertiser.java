package core;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import interfaces.Dashboard;

public class Advertiser extends User implements Dashboard {
    private AdManager adManager;

    public Advertiser(String id, String password) {
        super(id, password);
        this.adManager = new AdManager();
    }

    @Override
    public boolean displayDashboard() {
    	boolean exists = false;
    	
        try {
            java.io.File file = new java.io.File("src/data/advertisers.txt");
            java.util.Scanner reader = new java.util.Scanner(file);

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] parts = line.split("\\,");
                if (parts.length >= 2 && parts[0].equals(this.getId()) && parts[1].equals(this.getPassword())) {
                    exists = true;
                    break;
                }
            }
            reader.close();
        } catch (java.io.FileNotFoundException e) {
            System.out.println("Advertiser file not found.");
            return false;
        }

        if (exists) {
            System.out.println("\nWelcome to the Advertiser Dashboard, " + this.getId() + "!");
        } else {
            System.out.println("Advertiser not found or incorrect password.");
        }
        return exists;
        
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n=== Advertiser Menu ===");
            System.out.println("1. View Assigned Ads");
            System.out.println("2. Do Advertising");
            System.out.println("3. Check Payment Status");
            System.out.println("4. Logout");
            System.out.println("5. Exit Program");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: adManager.viewAds(); break;
                case 2: adManager.doAds(); break;
                case 3: adManager.checkPayment(); break;
                case 4: System.out.println("Logged Out Successfully."); break;
                case 5: System.out.println("Exited Successfully."); System.exit(0); break;
                default: System.out.println("Invalid choice."); break;
            }
        } while (choice != 4);
    }

    private class AdManager {
        
        public void viewAds() {
            System.out.println("\n[ Assigned Ads ]");
            try (Scanner scanner = new Scanner(new File("src/data/ads.txt"))) {
                boolean found = false;
                while (scanner.hasNextLine()) {
                    String[] parts = scanner.nextLine().split(",");
                    if (parts.length >= 6 && parts[3].equals(Advertiser.this.getId())) {
                        found = true;
                        System.out.println("Ad: " + parts[1]);
                        System.out.println("Campaign ID: " + parts[1]);
                        System.out.println("Influencer ID: " + parts[2]);
                        System.out.println("Status: " + parts[4]);
                        System.out.println("Payment: " + parts[5]);
                        System.out.println("----------------------");
                    }
                }
                if (!found) System.out.println("No ads assigned to you yet.");
            } catch (IOException e) {
                System.out.println("Error reading ads file.");
            }
        }

        public void doAds() {
            System.out.println("\n[ Mark Ad as Completed ]");
            List<String> updatedLines = new ArrayList<>();
            boolean foundPending = false;

            try (Scanner scanner = new Scanner(new File("src/data/ads.txt"))) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(",");
                    if (parts.length >= 6 && parts[0].equals(Advertiser.this.getId()) && parts[4].equalsIgnoreCase("Pending")) {
                        System.out.println("Completing ad for Campaign ID: " + parts[1]);
                        parts[4] = "Completed";
                        foundPending = true;
                        line = String.join(",", parts);
                    }
                    updatedLines.add(line);
                }
            } catch (IOException e) {
                System.out.println("Error reading ads file.");
                return;
            }

            if (!foundPending) {
                System.out.println("No pending ads to complete.");
                return;
            }

            try (PrintWriter writer = new PrintWriter("src/data/ads.txt")) {
                for (String updatedLine : updatedLines) {
                    writer.println(updatedLine);
                }
                System.out.println("Ad status updated successfully!");
            } catch (IOException e) {
                System.out.println("Error writing to ads file.");
            }
        }

        public void checkPayment() {
            System.out.println("\n[ Payment Status ]");
            try (Scanner scanner = new Scanner(new File("src/data/ads.txt"))) {
                boolean found = false;
                while (scanner.hasNextLine()) {
                    String[] parts = scanner.nextLine().split(",");
                    if (parts.length >= 6 && parts[0].equals(Advertiser.this.getId())) {
                        found = true;
                        System.out.println("Campaign ID: " + parts[1]);
                        System.out.println("Status: " + parts[4]);
                        System.out.println("Payment: " + parts[5]);
                        System.out.println("----------------------");
                    }
                }
                if (!found) System.out.println("No ads assigned to check payment status.");
            } catch (IOException e) {
                System.out.println("Error reading ads file.");
            }
        }
    }
}
