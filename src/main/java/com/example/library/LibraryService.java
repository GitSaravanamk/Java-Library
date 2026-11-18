package com.example.library;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class LibraryService {
    private final List<Book> books = new ArrayList<>();
    private final Path dataFile = Path.of("data", "books.txt");

    public LibraryService() throws IOException {
        loadBooks();
    }

    private void loadBooks() throws IOException {
        Files.createDirectories(dataFile.getParent());
        if (!Files.exists(dataFile)) {
            Files.createFile(dataFile);
            return;
        }

        for (String line : Files.readAllLines(dataFile)) {
            if (line.isBlank()) continue;
            try {
                books.add(Book.fromFileLine(line));
            } catch (RuntimeException ex) {
                System.err.println("Skipping invalid book record: " + line);
            }
        }
    }

    private void saveBooks() throws IOException {
        List<String> lines = new ArrayList<>();
        for (Book book : books) lines.add(book.toFileLine());
        Files.write(
            dataFile,
            lines,
            StandardOpenOption.CREATE,
            StandardOpenOption.TRUNCATE_EXISTING
        );
    }

    public void addBook(String title, String author, int copies) throws IOException {
        validateText(title, "Title");
        validateText(author, "Author");
        if (copies < 1) throw new IllegalArgumentException("Copies must be at least 1.");

        int nextId = books.stream().mapToInt(Book::getId).max().orElse(0) + 1;
        books.add(new Book(nextId, title.trim(), author.trim(), copies, copies));
        saveBooks();
        System.out.println("Book added successfully. Book ID: " + nextId);
    }

    public void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("No books found. Add a book first.");
            return;
        }

        System.out.printf("%-6s %-30s %-25s %-12s %-12s%n",
            "ID", "TITLE", "AUTHOR", "TOTAL", "AVAILABLE");
        System.out.println("-".repeat(90));

        for (Book book : books) {
            System.out.printf("%-6d %-30s %-25s %-12d %-12d%n",
                book.getId(),
                shorten(book.getTitle(), 28),
                shorten(book.getAuthor(), 23),
                book.getTotalCopies(),
                book.getAvailableCopies());
        }
    }

    public void searchBooks(String keyword) {
        String search = keyword.trim().toLowerCase();
        boolean found = false;

        for (Book book : books) {
            if (String.valueOf(book.getId()).equals(search)
                    || book.getTitle().toLowerCase().contains(search)
                    || book.getAuthor().toLowerCase().contains(search)) {
                System.out.printf("ID: %d | Title: %s | Author: %s | Available: %d/%d%n",
                    book.getId(), book.getTitle(), book.getAuthor(),
                    book.getAvailableCopies(), book.getTotalCopies());
                found = true;
            }
        }

        if (!found) System.out.println("No matching books found.");
    }

    public void issueBook(int id) throws IOException {
        Book book = findById(id);
        if (book == null) {
            System.out.println("Book ID not found.");
        } else if (!book.issueCopy()) {
            System.out.println("No copies are currently available.");
        } else {
            saveBooks();
            System.out.println("Book issued successfully: " + book.getTitle());
        }
    }

    public void returnBook(int id) throws IOException {
        Book book = findById(id);
        if (book == null) {
            System.out.println("Book ID not found.");
        } else if (!book.returnCopy()) {
            System.out.println("All copies are already in the library.");
        } else {
            saveBooks();
            System.out.println("Book returned successfully: " + book.getTitle());
        }
    }

    private Book findById(int id) {
        for (Book book : books) {
            if (book.getId() == id) return book;
        }
        return null;
    }

    private void validateText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " cannot be empty.");
        }
        if (value.contains("|") || value.contains("\n") || value.contains("\r")) {
            throw new IllegalArgumentException(field + " cannot contain | or line breaks.");
        }
    }

    private String shorten(String value, int maxLength) {
        return value.length() <= maxLength ? value : value.substring(0, maxLength - 3) + "...";
    }
}
