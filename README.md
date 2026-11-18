# Library Management System (Java)

A beginner-friendly console application for managing a small library. It demonstrates object-oriented programming, collections, input validation, and file handling.

## Features

- Add books with title, author, and number of copies
- View all books and available copy counts
- Search by book ID, title, or author
- Issue a copy (reduces available copies)
- Return a copy (increases available copies)
- Save records in `data/books.txt`, so records remain after restarting

## Requirements

- JDK 17 or later
- Command Prompt, PowerShell, or a terminal

No external libraries or database installation are required.

## Run on Windows

Open a terminal in this project folder.

1. Check Java:

   ```bash
   java -version
   javac -version
   ```

2. Compile:

   ```bash
   javac -d out src/main/java/com/example/library/*.java
   ```

3. Run:

   ```bash
   java -cp out com.example.library.Main
   ```

## Menu

```text
1. Add a book
2. View all books
3. Search books
4. Issue a book
5. Return a book
0. Exit
```

## Data storage

The program creates `data/books.txt` automatically. Each record is stored as:

```text
id|title|author|totalCopies|availableCopies
```

Keep this file if you want to retain your library records. Do not edit it while the program is running.

## Suggested manual tests

1. Add a book with 2 copies.
2. View all books and confirm available copies are 2.
3. Issue the book twice; available copies should become 0.
4. Try issuing it again; the program should reject the request.
5. Return one copy; available copies should become 1.
6. Exit and run the program again; confirm the book is still present.

## Concepts demonstrated

- Classes and objects
- Encapsulation
- ArrayList
- Loops and switch statements
- Exception handling and input validation
- Reading and writing files with Java NIO

## Future improvements

- Add student/member records
- Record who borrowed each book and the issue date
- Add due dates and overdue fine calculation
- Replace text-file storage with MySQL and JDBC
- Build a web interface using Spring Boot

## GitHub upload

Create a GitHub repository named `java-library-management-system`, then follow the Git commands in the main project guide.
