# Java Multithreaded Producer-Consumer Project

This project demonstrates a multithreaded producer-consumer model using Java's `ReentrantLock`. A single producer adds art and music-related strings to a shared buffer, while three consumer threads process and manipulate these strings by printing their length and reversing them.

## 📂 Project Structure

- `Main.java` – Entry point of the application that creates and starts the producer and consumer threads.
- `ThreadColor.java` – Defines ANSI color codes to visually differentiate output from each thread in the terminal.

## 🧵 Threads Involved

- **Producer:** Adds a fixed list of strings (representing genres or styles of art/music) to a shared list.
- **Consumers:** Three consumers access the shared buffer, printing the length of each string and its reverse until "EOF" is encountered.

## 🔧 Technologies Used

- Java
- Threading with `Runnable`
- `ReentrantLock` for thread-safe synchronization
- ANSI colors for readable terminal output

## 🚀 How to Run

1. Compile the program:
   ```bash
   javac Main.java ThreadColor.java
