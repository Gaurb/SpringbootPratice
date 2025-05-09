package com.example.CRUD.service;

import com.example.CRUD.model.JournalEntry;
import com.example.CRUD.model.User;
import com.example.CRUD.repository.JournalRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JournalService {
    private final JournalRepository journalRepository;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(JournalService.class);

    public List<JournalEntry> getAllEntries(String username) {
        User user = userService.findByUsername(username);
        if (user != null) {
            return user.getJournalEntryList();
        }else{
            return null;
        }
    }
    @Transactional
    public JournalEntry createEntry(JournalEntry journalEntry,String username) {
        try {
            User user = userService.findByUsername(username);
            if (user != null) {
                journalEntry.setCreatedAt(LocalDateTime.now());
                JournalEntry saved = journalRepository.save(journalEntry);
                user.getJournalEntryList().add(saved);
                userService.saveUser(user);
                return saved;
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error creating journal entry: " + e.getMessage());
        }

    }
    public JournalEntry updateEntry(ObjectId id,JournalEntry journalEntry) {
        return journalRepository.save(journalEntry);
    }

    public void deleteEntry(ObjectId id, String username) {
        User user = userService.findByUsername(username);
        user.getJournalEntryList().removeIf(entry -> entry.getId().equals(id));
        userService.createUser(user);
        journalRepository.deleteById(id);
    }

    public Optional<JournalEntry> getEntryById(ObjectId id) {

        return journalRepository.findById(id);
    }
}
