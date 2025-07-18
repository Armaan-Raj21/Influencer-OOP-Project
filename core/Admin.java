package core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import interfaces.Dashboard;

public class Admin extends User implements Dashboard {
    public Admin(String id, String password) {
        super(id, password);
    }

    @Override
    public boolean displayDashboard() {
    	boolean exists = false;
    	
        try {
            java.io.File file = new java.io.File("src/data/admins.txt");
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
            System.out.println("Admin file not found.");
            return false;
        }

        if (exists) {
            System.out.println("\nWelcome to the Admin Dashboard, " + this.getId() + "!");
        } else {
            System.out.println("Admin not found or incorrect password.");
        }
        return exists;
        
    }

    @Override
    public void showMenu() {
    	Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("1. View Influencers");
            System.out.println("2. View Campaigns");
            System.out.println("3. Update Campaign Stats");
            System.out.println("4. Logout");
            System.out.println("5. Exit Program");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: viewInfluencers(); break;
                case 2: viewCampaigns(); break;
                case 3: updateCampaignStats(); break;
                case 4: System.out.println("Logged Out Successfully."); break;
                case 5: System.out.println("Exited Successfully."); System.exit(0); break;
                default: System.out.println("Invalid choice."); break;
            }
        } while (choice != 4);
    }
    
    private void updateCampaignStats() {
    	Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Campaign ID: ");
        String campaignId = scanner.nextLine();

        System.out.print("Enter Total Likes: ");
        int likes = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Total Comments: ");
        int comments = Integer.parseInt(scanner.nextLine());

        String stats = campaignId + "," + likes + "," + comments;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/data/campaignStats.txt", true))) {
            writer.write(stats);
            writer.newLine();
            System.out.println("Campaign stats updated successfully.");
        } catch (IOException e) {
            System.out.println("Error writing campaign stats: " + e.getMessage());
        }
    }
		


	private void viewInfluencers() {
		File file = new File("src/data/influencers.txt");
        try (Scanner reader = new Scanner(file)) {
            System.out.println("=== All Influencer Profiles ===");
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] parts = line.split(",");
                System.out.println("ID: " + parts[0] + ", Content Type: " + parts[2] + ", Followers: " + parts[3] + ", Engagement Rate: " + parts[4]);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Influencer file not found.");
        }
		
	}

	private void viewCampaigns() {
		File file = new File("src/data/campaigns.txt");
        try (Scanner reader = new Scanner(file)) {
            System.out.println("=== All Campaigns ===");
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] parts = line.split(",");
                System.out.println("Campaign ID: " + parts[0] + ", Brand: " + parts[1] + ", Content Type: " + parts[2] + ", Budget: ₹" + parts[3]);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Campaign file not found.");
        }
	}
}
