package app;

import core.*;
import exceptions.LoginException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    private static volatile boolean exitApp = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (!exitApp) {
            Thread loginThread = new Thread(new LoginHandler(scanner));
            loginThread.start();

            try {
                loginThread.join();
            } catch (InterruptedException e) {
                System.err.println("Login session interrupted.");
            }
        }

        System.out.println("Thank you for using the program!");
        scanner.close();
    }

    static class LoginHandler implements Runnable {
        private Scanner scanner;

        LoginHandler(Scanner scanner) {
            this.scanner = scanner;
        }

        @Override
        public void run() {
            try {
            	System.out.println("\n===== WELCOME TO SOCIAL MEDIA COLLABORATION PROGRAM =====\n");
                System.out.println("Enter 0 to Exit or 1 to Continue:");
                String choice = scanner.nextLine();

                if (choice.equals("0")) {
                    exitApp = true;
                    return;
                }
                
                System.out.print("Do you have an account? (yes/no): ");
                String hasAccount = scanner.nextLine();

                if (hasAccount.equalsIgnoreCase("no")) {
                    register(scanner);
                    return;
                }
                
                System.out.println("===== LOGIN =====");
                System.out.print("Enter ID: ");
                String id = scanner.nextLine();
                System.out.print("Enter Password: ");
                String password = scanner.nextLine();
                System.out.print("Are you an Influencer, Brand Manager, Admin, or Advertiser? (I/B/A/D): ");
                String userType = scanner.nextLine();

                User user;
                if (userType.equalsIgnoreCase("I")) {
                    user = new Influencer(id, password);
                } else if (userType.equalsIgnoreCase("B")) {
                    user = new BrandManager(id, password);
                } else if (userType.equalsIgnoreCase("A")) {
                    user = new Admin(id, password);
                } else if (userType.equalsIgnoreCase("D")) {
                    user = new Advertiser(id, password);
                } else {
                    throw new LoginException("Invalid user type selected.");
                }

                if (user.displayDashboard()) {
                    user.showMenu();
                }

            } catch (LoginException e) {
                System.err.println("Login failed: " + e.getMessage());
            }
        }
    }

    public static void register(Scanner scanner) {
        System.out.print("Register as Influencer, Brand Manager, Admin, or Advertiser? (I/B/A/D): ");
        String userType = scanner.nextLine();
        System.out.print("Enter ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        User user;
        if (userType.equalsIgnoreCase("I")) {
            user = new Influencer(id, password);
            System.out.println("New Influencer Created!");
            try (FileWriter fw = new FileWriter("src/data/influencers.txt", true)) {
                fw.write("\n" + id + "," + password + ",null," + String.valueOf(0) + "," + String.valueOf(0.0));
            } catch (IOException e) {
                System.err.println("Error saving influencer to file: " + e.getMessage());
            }
        } else if (userType.equalsIgnoreCase("B")) {
            user = new BrandManager(id, password);
            System.out.println("New Brand Created!");
            try (FileWriter fw = new FileWriter("src/data/brandmanager.txt", true)) {
                fw.write("\n" + id + "," + password);
            } catch (IOException e) {
                System.err.println("Error saving brand to file: " + e.getMessage());
            }
        } else if (userType.equalsIgnoreCase("A")) {
            user = new Admin(id, password);
            System.out.println("New Admin Created!");
            try (FileWriter fw = new FileWriter("src/data/admins.txt", true)) {
                fw.write("\n" + id + "," + password);
            } catch (IOException e) {
                System.err.println("Error saving admin to file: " + e.getMessage());
            }
        } else if (userType.equalsIgnoreCase("D")) {
            user = new Advertiser(id, password);
            System.out.println("New Advertiser Created!");
            try (FileWriter fw = new FileWriter("src/data/advertisers.txt", true)) {
                fw.write("\n" + id + "," + password);
            } catch (IOException e) {
                System.err.println("Error saving advertiser to file: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid user type selected.");
        }
    }
}
