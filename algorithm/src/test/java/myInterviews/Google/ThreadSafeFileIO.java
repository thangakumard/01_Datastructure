package myInterviews.Google;

import org.testng.annotations.Test;

import java.io.*;
import java.nio.file.*;

public class ThreadSafeFileIO {

    private final Path filePath;
    private final Object readWriteLock = new Object();

    public ThreadSafeFileIO(String fileName) throws IOException {
        this.filePath = Paths.get(fileName);
        // Create the file if it doesn't exist
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
    }

    /**
     * Writes data to the file in a thread-safe manner.
     *
     * @param data The data to write to the file.
     * @throws IOException If an I/O error occurs.
     */
    public void writeToFile(String data) throws IOException {
        synchronized (readWriteLock) {
            try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.APPEND)) {
                writer.write(data);
                writer.newLine();
            }
        }
    }

    /**
     * Reads all data from the file in a thread-safe manner.
     *
     * @return The content of the file as a string.
     * @throws IOException If an I/O error occurs.
     */
    public String readFromFile() throws IOException {
        synchronized (readWriteLock) {
            StringBuilder content = new StringBuilder();
            try (BufferedReader reader = Files.newBufferedReader(filePath)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append(System.lineSeparator());
                }
            }
            return content.toString();
        }
    }

    /**
     * Clears the file content in a thread-safe manner.
     *
     * @throws IOException If an I/O error occurs.
     */
    public void clearFile() throws IOException {
        synchronized (readWriteLock) {
            try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.TRUNCATE_EXISTING)) {
                writer.write("");
            }
        }
    }

    @Test
    public void Test(String[] args) {
        try {
            ThreadSafeFileIO fileIO = new ThreadSafeFileIO("example.txt");

            // Example usage
            Thread writerThread1 = new Thread(() -> {
                try {
                    fileIO.writeToFile("Hello from Thread 1");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            Thread writerThread2 = new Thread(() -> {
                try {
                    fileIO.writeToFile("Hello from Thread 2");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            Thread readerThread = new Thread(() -> {
                try {
                    System.out.println(fileIO.readFromFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            writerThread1.start();
            writerThread2.start();
            writerThread1.join();
            writerThread2.join();
            readerThread.start();
            readerThread.join();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

