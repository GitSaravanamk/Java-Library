package com.example.library;

public class Book {
    private final int id;
    private final String title;
    private final String author;
    private final int totalCopies;
    private int availableCopies;

    public Book(int id, String title, String author, int totalCopies, int availableCopies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getTotalCopies() { return totalCopies; }
    public int getAvailableCopies() { return availableCopies; }

    public boolean issueCopy() {
        if (availableCopies <= 0) return false;
        availableCopies--;
        return true;
    }

    public boolean returnCopy() {
        if (availableCopies >= totalCopies) return false;
        availableCopies++;
        return true;
    }

    public String toFileLine() {
        return id + "|" + title + "|" + author + "|" + totalCopies + "|" + availableCopies;
    }

    public static Book fromFileLine(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length != 5) throw new IllegalArgumentException("Invalid book record");
        return new Book(
            Integer.parseInt(parts[0]),
            parts[1],
            parts[2],
            Integer.parseInt(parts[3]),
            Integer.parseInt(parts[4])
        );
    }
}
