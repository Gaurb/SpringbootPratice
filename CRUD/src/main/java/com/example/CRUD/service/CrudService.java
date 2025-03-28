package com.example.CRUD.service;

import com.example.CRUD.model.Student;
import com.example.CRUD.repository.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CrudService {
    @Autowired
    private CrudRepository crudRepository;

    public List<Student> getAllStudent(){
        return crudRepository.findAll();
    }

    public Student createStudent(Student s){
        return crudRepository.save(s);
    }

    public Optional<Student> getStudent(int id){
        return crudRepository.findById(id);
    }

    public void deleteStudent(int id){
        crudRepository.deleteById(id);
    }
    public Optional<Student> updateStudent(Student s){
        Optional<Student> oldstudent=crudRepository.findById(s.getId());
        if(oldstudent.isPresent()){
            Student student=oldstudent.get();
            student.setName(s.getName());
            student.setLocation(s.getLocation());
            return Optional.of(student);
        }else
            return Optional.empty();
    }
}
