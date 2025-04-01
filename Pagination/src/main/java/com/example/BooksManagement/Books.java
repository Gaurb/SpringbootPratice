package com.example.BooksManagement;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "books")
public class Books {
    @Id
    private String id;
    private String title;
    private String author;
    private double price;


}
