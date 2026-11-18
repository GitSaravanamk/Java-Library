package com.example.library;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            LibraryService library = new LibraryService();
            boolean running = true;

            System.out.println("==================================");
            System.out.println("     LIBRARY MANAGEMENT SYSTEM");
            System.out.println("==================================");

            while (running) {
                printMenu();
                System.out.print("Enter your choice: ");
                String choice = scanner.nextLine().trim();

                try {
                    switch (choice) {
                        case "1" -> {
                            System.out.print("Enter book title: ");
                            String title = scanner.nextLine();
                            System.out.print("Enter author name: ");
                            String author = scanner.nextLine();
                            int copies = readPositiveInt(scanner, "Enter number of copies: ");
                            library.addBook(title, author, copies);
                        }
                        case "2" -> library.displayBooks();
                        case "3" -> {
                            System.out.print("Enter book ID, title, or author to search: ");
                            library.searchBooks(scanner.nextLine());
                        }
                        case "4" -> {
                            int id = readPositiveInt(scanner, "Enter book ID to issue: ");
                            library.issueBook(id);
                        }
                        case "5" -> {
                            int id = readPositiveInt(scanner, "Enter book ID to return: ");
                            library.returnBook(id);
                        }
                        case "0" -> {
                            running = false;
                            System.out.println("Thank you for using the Library Management System.");
                        }
                        default -> System.out.println("Invalid choice. Please select from the menu.");
                    }
                } catch (IllegalArgumentException ex) {
                    System.out.println("Input error: " + ex.getMessage());
                } catch (IOException ex) {
                    System.out.println("Could not save data: " + ex.getMessage());
                }
                System.out.println();
            }
        } catch (IOException ex) {
            System.out.println("Unable to start the application: " + ex.getMessage());
        }
    }

    private static void printMenu() {
        System.out.println("1. Add a book");
        System.out.println("2. View all books");
        System.out.println("3. Search books");
        System.out.println("4. Issue a book");
        System.out.println("5. Return a book");
        System.out.println("0. Exit");
    }

    private static int readPositiveInt(Scanner scanner, String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        try {
            int value = Integer.parseInt(input);
            if (value < 1) throw new NumberFormatException();
            return value;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Please enter a whole number greater than zero.");
        }
    }
}
