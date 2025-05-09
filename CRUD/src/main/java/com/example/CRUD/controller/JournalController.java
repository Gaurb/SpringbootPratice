package com.example.CRUD.controller;

import com.example.CRUD.model.JournalEntry;
import com.example.CRUD.model.User;
import com.example.CRUD.service.JournalService;
import com.example.CRUD.service.UserService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
@RequiredArgsConstructor
public class JournalController {
    private final JournalService journalService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        List<JournalEntry> all=journalService.getAllEntries(username);
        if(all!=null){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("id/{myId}")
    public ResponseEntity<?> getEntryById(@PathVariable ObjectId myId) {
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        User user=userService.findByUsername(username);
       List<JournalEntry> all = user.getJournalEntryList().stream()
                .filter(entry -> entry.getId().equals(myId))
                .collect(Collectors.toList());
       if(!all.isEmpty()){
           Optional<JournalEntry> journalEntry=journalService.getEntryById(myId);
           if(journalEntry.isPresent()){
               return new ResponseEntity<>(journalEntry.get(),HttpStatus.OK);
           }
       }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry) {
            String username= SecurityContextHolder.getContext().getAuthentication().getName();
            JournalEntry newEntry=journalService.createEntry(journalEntry,username);
            if(newEntry!=null)
                return new ResponseEntity<>(newEntry, HttpStatus.CREATED);
            else
                return new ResponseEntity<>( HttpStatus.BAD_REQUEST);


    }

    @PutMapping("/{myId}")
    public ResponseEntity<?> updateEntry(@PathVariable ObjectId myId,@RequestBody JournalEntry journalEntry) {
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<JournalEntry> oldEntry=journalService.getEntryById(myId);
        if(oldEntry.isPresent()){
            JournalEntry old=oldEntry.get();
            old.setTitle(!journalEntry.getTitle().isEmpty() ? journalEntry.getTitle() : old.getTitle());
            old.setContent(journalEntry.getContent()!=null && !journalEntry.getContent().isEmpty() ? journalEntry.getContent() : old.getContent());
            JournalEntry updatedEntry=journalService.updateEntry(myId,old);
            return new ResponseEntity<>(updatedEntry,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/{myId}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId myId) {
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        journalService.deleteEntry(myId,username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
