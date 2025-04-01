package com.example.BooksManagement;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final BookRepository bookRepository;
    private final Random random = new Random();

    @Override
    public void run(String... args) {
        if (bookRepository.count() == 0) { // Avoid duplicate data
            List<String> titles = List.of("Spring Boot in Action", "Clean Code", "Effective Java",
                    "Java Concurrency", "Microservices Design", "System Design", "Data Structures",
                    "Algorithms Unlocked", "Machine Learning with Java", "Kubernetes in Action");

            List<String> authors = List.of("Craig Walls", "Robert C. Martin", "Joshua Bloch",
                    "Brian Goetz", "John Doe", "Martin Fowler", "Donald Knuth", "Andrew Ng", "Ian Goodfellow");

            List<Books> books = IntStream.rangeClosed(1, 50)
                    .mapToObj(i -> new Books(
                            null,
                            titles.get(random.nextInt(titles.size())) + " - Vol " + i,
                            authors.get(random.nextInt(authors.size())),
                            20 + (random.nextDouble() * 50) // Random price between 20 - 70
                    )).toList();

            bookRepository.saveAll(books);
            System.out.println("📚 50+ Sample books added to MongoDB!");
        }
    }
}
