package com.example.BooksManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BooksController {
    @Autowired
    private  BookRepository bookRepository;

//    Add a book: POST /books
        @PostMapping
        public Books createBook(@RequestBody Books books){
            return bookRepository.save(books);
        }

//    Get all books: GET /books
        @GetMapping("/all")
        public List<Books> getBook1(){
            return bookRepository.findAll();
        }
        @GetMapping
        public Page<Books> getBook(Pageable pageable){
            return bookRepository.findAll(pageable);
        }

//    Get a book by ID: GET /books/{id}
        @GetMapping("/{id}")
        public Books getBookById( @PathVariable String id){
            return bookRepository.findById(id).orElse(null);
        }

//    Update a book: PUT /books/{id}
        @PutMapping("/{id}")
        public Books updateBook(@PathVariable String id,@RequestBody Books books){
            books.setId(id);
            return bookRepository.save(books);
        }
//    Delete a book: DELETE /books/{id}
    @DeleteMapping("/{id}")
        public String deleteId(@PathVariable String id){
            bookRepository.deleteById(id);
            return "Book Deleted";
        }


}
