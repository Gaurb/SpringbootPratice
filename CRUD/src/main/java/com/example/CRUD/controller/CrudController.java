package com.example.CRUD.controller;

import com.example.CRUD.model.Student;
import com.example.CRUD.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CrudController {
    @Autowired
    private CrudService crudService;

    @GetMapping("/hello")
    public String Hello(){
        return "Hello";
    }

    @GetMapping("/students")
    public List<Student> getAllStudent(){
        return crudService.getAllStudent();
    }
    @PostMapping("/create")
    public Student createStudent(@RequestBody Student student){
        return crudService.createStudent(student);
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable int id){
        return crudService.getStudent(id).get();
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable int id){
        crudService.deleteStudent(id);
    }

    @PutMapping("/update")
    public Student UpdateStudent(@RequestBody Student s){
        return crudService.updateStudent(s).get();
    }


}
