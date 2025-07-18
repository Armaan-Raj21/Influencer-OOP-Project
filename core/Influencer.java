package core;

import java.io.*;
import java.util.*;
import interfaces.Dashboard;

public class Influencer extends User implements Dashboard {
	private String contentType;
    private int followers;
    private double engagementRate;

    public Influencer(String id, String password) {
        super(id, password);
        this.contentType = null;
        this.followers = 0;
        this.engagementRate = 0.0;
    }

    public Influencer(String id, String password, String contentType, int followers, double engagementRate) {
        super(id, password);
        this.followers = followers;
        this.engagementRate = engagementRate;
    }
    
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    
    public String getContentType() {
    	try (Scanner sc = new Scanner(new File("src/data/influencers.txt"))) {
            while (sc.hasNextLine()) {
                String[] parts = sc.nextLine().split(",");
                if (parts.length >= 5 && parts[0].equals(this.getId())) {
                    this.contentType = parts[2];
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Influencer file not found.");
        }
        return this.contentType;
    }
    
    public double getEngagementRate() {
    	try (Scanner sc = new Scanner(new File("src/data/influencers.txt"))) {
            while (sc.hasNextLine()) {
                String[] parts = sc.nextLine().split(",");
                if (parts.length >= 5 && parts[0].equals(this.getId())) {
                	this.engagementRate = Double.parseDouble(parts[4]);
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Influencer file not found.");
        }
		return this.engagementRate;
	}
    
    public int getFollowers() {
    	try (Scanner sc = new Scanner(new File("src/data/influencers.txt"))) {
            while (sc.hasNextLine()) {
                String[] parts = sc.nextLine().split(",");
                if (parts.length >= 5 && parts[0].equals(this.getId())) {
                	this.followers = Integer.parseInt(parts[3]);
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Influencer file not found.");
        }
    	return this.followers;
    }

    @Override
    public boolean displayDashboard() {
    	boolean exists = false;
    	
        try {
            java.io.File file = new java.io.File("src/data/influencers.txt");
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
            System.out.println("Influencer file not found.");
            return false;
        }

        if (exists) {
            System.out.println("\nWelcome to the Influencer Dashboard, " + this.getId() + "!");
        } else {
            System.out.println("Influencer not found or incorrect password.");
        }
        return exists;
        
    }

    @Override
    public void showMenu() {
    	Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n=== Influencer Menu ===");
            System.out.println("1. View Profile");
            System.out.println("2. View Campaigns");
            System.out.println("3. Participate in Campaign");
            System.out.println("4. Update Stats");
            System.out.println("5. View Contracts & Accept/Reject");
            System.out.println("6. View Payments");
            System.out.println("7. Logout");
            System.out.println("8. Exit Program");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: viewProfile(); break;
                case 2: viewCampaigns(); break;
                case 3: takePart(); break;
                case 4: updateStats(); break;
                case 5: viewContracts(); break;
                case 6: viewPayments(); break;
                case 7: System.out.println("Logged Out Successfully."); break;
                case 8: System.out.println("Exited Successfully."); System.exit(0); break;
                default: System.out.println("Invalid choice."); break;
            }
        } while (choice != 7);
    }

    private void takePart() {
    	Scanner scanner = new Scanner(System.in);
    	System.out.print("Enter Contract ID: ");
        String contractId = scanner.nextLine();
        System.out.print("Enter Campaign ID: ");
        String campaignId = scanner.nextLine();
        System.out.print("Enter Brand ID: ");
        String BrandId = scanner.nextLine();

        String contract = "inf," + contractId + "," + BrandId + "," + campaignId + "," + this.getId() + ",Pending,Pending";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/data/contracts.txt", true))) {
            writer.write(contract);
            writer.newLine();
            System.out.println("Contract initiated successfully!");
        } catch (IOException e) {
            System.out.println("Error creating contract: " + e.getMessage());
        }
	}

	private void viewPayments() {
    	try (Scanner sc = new Scanner(new File("src/data/payments.txt"))) {
            boolean found = false;
            while (sc.hasNextLine()) {
                String[] parts = sc.nextLine().split(",");
                if (parts.length >= 5 && parts[2].equals(this.getId())) {
                    System.out.println("\nPayment ID: " + parts[1]);
                    System.out.println("Amount: " + parts[3]);
                    System.out.println("Status: " + parts[4]);
                    found = true;
                }
            }
            if (!found) System.out.println("No payments found for you.");
        } catch (FileNotFoundException e) {
            System.out.println("Payments file not found.");
        }
	}

	private void viewProfile() {
        System.out.println("ID: " + getId());
        System.out.println("Content Type: " + this.getContentType());
        System.out.println("Followers: " + this.getFollowers());
        System.out.println("Engagement Rate: " + this.getEngagementRate());
    }

    private void viewCampaigns() {
    	try (Scanner sc = new Scanner(new File("src/data/campaigns.txt"))) {
            boolean found = false;
            while (sc.hasNextLine()) {
                String[] parts = sc.nextLine().split(",");
                if (parts.length >= 4 && parts[2].equals(this.getContentType())) {
                    System.out.println("\nCampaign ID: " + parts[0]);
                    System.out.println("Brand ID: " + parts[1]);
                    System.out.println("Content Type: " + parts[2]);
                    System.out.println("Budget: " + parts[3]);
                    found = true;
                }
            }
            if (!found) System.out.println("No matching campaigns.");
        } catch (FileNotFoundException e) {
            System.out.println("Campaign file not found.");
        }
    }

    private void updateStats() {
    	Scanner input = new Scanner(System.in);
    	System.out.print("New Followers: ");
        contentType = input.nextLine();
        System.out.print("New Followers: ");
        followers = Integer.parseInt(input.nextLine());
        System.out.print("New Engagement Rate: ");
        engagementRate = Double.parseDouble(input.nextLine());

        File file = new File("src/data/influencers.txt");
        List<String> lines = new ArrayList<>();

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(",");
                if (parts.length >= 5 && parts[0].equals(getId())) {
                	parts[2] = contentType;
                    parts[3] = String.valueOf(followers);
                    parts[4] = String.valueOf(engagementRate);
                    line = String.join(",", parts);
                }
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Influencer file not found.");
            return;
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            for (String l : lines) pw.println(l);
            System.out.println("Stats updated successfully!");
        } catch (IOException e) {
            System.out.println("Error updating file.");
        }
    }
    
    private void viewContracts() {
        try (Scanner sc = new Scanner(new File("src/data/contracts.txt"))) {

            boolean found = false;
            List<String> lines = new ArrayList<>();
            Scanner input = new Scanner(System.in);

            while (sc.hasNextLine()) {
                lines.add(sc.nextLine());
            }

            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    if (parts[2].equals(this.getId()) || parts[4].equals(this.getId())) {
                        System.out.println("\nCreated By: " + parts[0]);
                        System.out.println("Contract ID: " + parts[1]);
                        System.out.println("Campaign ID: " + parts[3]);
                        System.out.println("Contract Status: " + parts[5]);
                        System.out.println("Payment Status: " + parts[6]);
                        found = true;
                    }
                }
            }

            if (!found) {
                System.out.println("No contracts found for you.");
                return;
            }

            System.out.print("\nDo you want to accept/reject any contract? (yes/no): ");
            String choice = input.nextLine().trim().toLowerCase();

            if (choice.equals("yes")) {
                System.out.print("Enter Contract ID: ");
                String contractId = input.nextLine().trim();

                System.out.print("Enter your decision (accept/reject): ");
                String decision = input.nextLine().trim().toLowerCase();

                if (!decision.equals("accept") && !decision.equals("reject")) {
                    System.out.println("Invalid decision. Only 'accept' or 'reject' allowed.");
                    return;
                }

                boolean updated = false;
                for (int i = 0; i < lines.size(); i++) {
                    String[] parts = lines.get(i).split(",");
                    if (parts.length >= 7 && parts[1].equals(contractId)) {
                        if (this.getId().equals(parts[2])) { 
                            parts[5] = decision;
                        } else if (this.getId().equals(parts[4])) { 
                            parts[5] = decision;
                        } else {
                            System.out.println("You are not authorized to update this contract.");
                            return;
                        }
                        lines.set(i, String.join(",", parts));
                        updated = true;
                        break;
                    }
                }

                if (updated) {
                    try (PrintWriter pw = new PrintWriter(new File("src/data/contracts.txt"))) {
                        for (String l : lines) {
                            pw.println(l);
                        }
                    }
                    System.out.println("Contract updated successfully!");
                } else {
                    System.out.println("Contract ID not found.");
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Contracts file not found.");
        } catch (IOException e) {
            System.out.println("Error updating the contract.");
        }
    }
}
