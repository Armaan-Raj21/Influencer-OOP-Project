package core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import exceptions.InvalidPaymentException;
import exceptions.PaymentProcessingException;
import interfaces.Dashboard;
import interfaces.PaymentHandler;

public class BrandManager extends User implements Dashboard, PaymentHandler  {

    public BrandManager(String id, String password) {
        super(id, password);
    }

    
    @Override
    public boolean displayDashboard() {
        boolean exists = false;
        	
	        try {
	            java.io.File file = new java.io.File("src/data/brandmanager.txt");
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
	            System.out.println("Brand file not found.");
	            return false;
	        }
	
	        if (exists) {
	            System.out.println("\nWelcome to the Brand Manager Dashboard, " + this.getId() + "!");
	        } else {
	            System.out.println("Brand not found or incorrect password.");
	        }
        return exists;
    }
    
    @Override
    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n=== Brand Manager Menu ===");
            System.out.println("0. Discover Influencers");
            System.out.println("1. Create Campaign");
            System.out.println("2. View Campaigns");
            System.out.println("3. Advertise");
            System.out.println("4. Manage Contracts");
            System.out.println("5. View Contracts");
            System.out.println("6. Process Payments");
            System.out.println("7. Track Campaign Performance");
            System.out.println("8. Logout");
            System.out.println("9. Exit Program");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 0: discoverInfluencers(); break;
                case 1: createCampaigns(); break;
                case 2: viewCampaigns(); break;
                case 3: assignAds(); break;
                case 4: manageContracts(); break;
                case 5: viewContracts(); break;
                case 6: processPayments(); break;
                case 7: trackCampaignPerformance(); break;
                case 8: System.out.println("Logged Out Successfully."); break;
                case 9: System.out.println("Exited Successfully."); System.exit(0); break;
                default: System.out.println("Invalid choice."); break;
            }
        } while (choice != 8);
    }


	private void viewContracts() {
		System.out.println("\n--- Your Contracts ---");
	    try (Scanner reader = new Scanner(new File("src/data/contracts.txt"))) {
	        boolean found = false;
	        while (reader.hasNextLine()) {
	            String line = reader.nextLine();
	            String[] parts = line.split(",");
	            if (parts.length >= 4 && parts[0].equals(this.getId())) {
	                System.out.println("ID: " + parts[0] + " | Influencer: " + parts[2] + " | Payment Status: " + parts[3]);
	                found = true;
	            }
	        }
	        if (!found) System.out.println("No campaigns found for you.");
	    } catch (Exception e) {
	        System.out.println("Could not read campaigns file.");
	    }
	}


	private void trackCampaignPerformance() {
	    Scanner scanner = new Scanner(System.in);
	    System.out.print("Enter Campaign ID: ");
	    String campaignId = scanner.nextLine();

	    try (Scanner reader = new Scanner(new File("src/data/campaignStats.txt"))) {
	        boolean found = false;
	        while (reader.hasNextLine()) {
	            String line = reader.nextLine();
	            String[] parts = line.split(",");
	            if (parts.length >= 3 && parts[0].equals(campaignId)) {
	                System.out.println("\nCampaign ID: " + parts[0]);
	                System.out.println(parts[1]);
	                System.out.println(parts[2]);
	                found = true;
	            }
	        }
	        if (!found) {
	            System.out.println("No performance stats found for this campaign.");
	        }
	    } catch (Exception e) {
	        System.out.println("Error reading campaign stats file.");
	    }
	}


	private void processPayments() {
		 Scanner scanner = new Scanner(System.in);

	    System.out.print("Enter Campaign ID: ");
	    String campaignId = scanner.nextLine();

	    System.out.print("Is this payment for (1) Influencer or (2) Advertiser? Enter 1 or 2: ");
	    int type = Integer.parseInt(scanner.nextLine());

	    String recipientId;
	    String recipientType;

	    if (type == 1) {
	        System.out.print("Enter Influencer ID: ");
	        recipientId = scanner.nextLine();
	        recipientType = "Influencer";
	    } else if (type == 2) {
	        System.out.print("Enter Advertiser ID: ");
	        recipientId = scanner.nextLine();
	        recipientType = "Advertiser";
	    } else {
	        System.out.println("Invalid option. Payment aborted.");
	        return;
	    }

	    System.out.print("Enter Amount: ₹");
	    double amount = Double.parseDouble(scanner.nextLine());

	    PaymentProcessor processor = new PaymentProcessor(
	        generatePaymentId(), this.getId(), recipientId, campaignId, amount, "Pending"
	    );

	    try {
	        if (campaignId.isEmpty()) {
	            processor.processPayment(this.getId(), recipientId, amount);
	        } else {
	            processor.processPayment(this.getId(), recipientId, campaignId, amount);
	        }
	        System.out.println("Payment completed successfully!");
	    } catch (PaymentProcessingException e) {
	        System.out.println("Error creating payment request: " + e.getMessage());
	    }
	}

	private String generatePaymentId() {
	    return "PAY-" + System.currentTimeMillis();
	}


	private void manageContracts() {
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
                    if (parts[2].equals(this.getId())) {
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



	private void discoverInfluencers() {
		List<List<String>> recom = new ArrayList<>();
		Scanner scanner = new Scanner(System.in);
        System.out.print("Enter campaign topic: ");
        String topic = scanner.nextLine();
        System.out.print("Enter minimum engagement rate: ");
        double minEngagement = scanner.nextDouble();
		try (Scanner fileScanner = new Scanner(new File("src/data/influencers.txt"))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String id = parts[0];
                    String password = parts[1];
                    String contentType = parts[2];
                    int followers = Integer.parseInt(parts[3]);
                    double engagementRate = Double.parseDouble(parts[4]);
                    if(topic.equals(contentType) && minEngagement<=engagementRate) {
                    	recom.add(Arrays.asList(id,String.valueOf(followers),String.valueOf(engagementRate)));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Influencer data file not found.");
            return;
        }

        System.out.println("\nRecommended Influencers:");
        if (recom.isEmpty()) {
            System.out.println("No influencers found matching the criteria.");
        } else {
            for (List<String> inf : recom) {
                System.out.println(inf);
            }
        }
	}


	private void createCampaigns() {
		Scanner scanner = new Scanner(System.in);
	    System.out.print("Enter Campaign ID: ");
	    String id = scanner.nextLine();
	    System.out.print("Enter Campaign Topic: ");
	    String topic = scanner.nextLine();
	    System.out.print("Enter Campaign Budget: ");
	    String budget = scanner.nextLine();

	    String campaignData = id + "," + this.getId() + "," + topic + "," + budget;

	    try (java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter("src/data/campaigns.txt", true))) {
	        writer.write(campaignData);
	        writer.newLine();
	        System.out.println("Campaign created successfully!");
	    } catch (java.io.IOException e) {
	        System.out.println("Error creating campaign: " + e.getMessage());
	    }
	    
	    String statsData = id + ",0,0";
	    try (java.io.BufferedWriter statsWriter = new java.io.BufferedWriter(new java.io.FileWriter("src/data/campaign-stats.txt", true))) {
	        statsWriter.write(statsData);
	        statsWriter.newLine();
	        System.out.println("Campaign stats initialized!");
	    } catch (java.io.IOException e) {
	        System.out.println("Error initializing campaign stats: " + e.getMessage());
	    }
	}


	private void viewCampaigns() {
		System.out.println("\n--- Your Campaigns ---");
	    try (Scanner reader = new Scanner(new File("src/data/campaigns.txt"))) {
	        boolean found = false;
	        while (reader.hasNextLine()) {
	            String line = reader.nextLine();
	            String[] parts = line.split(",");
	            if (parts.length >= 4 && parts[1].equals(this.getId())) {
	                System.out.println("ID: " + parts[0] + " | Topic: " + parts[2] + " | Budget: " + parts[3]);
	                found = true;
	            }
	        }
	        if (!found) System.out.println("No campaigns found for you.");
	    } catch (Exception e) {
	        System.out.println("Could not read campaigns file.");
	    }
	}
	
	private void assignAds() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter Advertisement ID: ");
	    String adId = scanner.nextLine();
	    System.out.print("Enter Campaign ID: ");
	    String campaignId = scanner.nextLine();
	    System.out.print("Enter Influencer ID: ");
	    String influencerId = scanner.nextLine();
	    System.out.print("Enter Advertiser ID: ");
	    String advertiserId = scanner.nextLine();

	    String assignment = adId + "," + this.getId() + "," + campaignId + "," + influencerId + "," + advertiserId + ",Pending";

	    try (java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter("src/data/ads.txt", true))) {
	    	writer.write(assignment);
	    	writer.newLine();
	        System.out.println("Ad assignment successful!");
	    } catch (Exception e) {
	        System.out.println("Error assigning ad: " + e.getMessage());
	    }
	}
  private void createContract() {
      Scanner scanner = new Scanner(System.in);
      System.out.print("Enter Contract ID: ");
      String contractId = scanner.nextLine();
      System.out.print("Enter Influencer ID: ");
      String influencerId = scanner.nextLine();
      System.out.print("Enter Campaign ID: ");
      String campaignId = scanner.nextLine();
      System.out.print("Enter Contract Terms: ");
      String terms = scanner.nextLine();

      Contract contract = new Contract(contractId, this.getId(), influencerId, campaignId, terms, "Pending");

      try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/data/contracts.txt", true))) {
    	  writer.write(contract.toString());
    	  writer.newLine();
          System.out.println("Contract created successfully!");
      } catch (IOException e) {
          System.out.println("Error creating contract: " + e.getMessage());
      }
  }


	@Override
	public void validatePayment(double amount) throws InvalidPaymentException {
        if(amount <= 0) throw new InvalidPaymentException("Invalid payment amount: " + amount);
    }

}
