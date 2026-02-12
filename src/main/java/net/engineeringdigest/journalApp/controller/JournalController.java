package net.engineeringdigest.journalApp.controller;

import lombok.RequiredArgsConstructor;
import net.engineeringdigest.journalApp.entity.JournalEntry;

import net.engineeringdigest.journalApp.services.JournalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequiredArgsConstructor
@RequestMapping("/journal")
public class JournalController {

    private final JournalService journalService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllEntries() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        List<JournalEntry> entries = journalService.getAllUserEntries(username);
        if (entries.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(entries);
    }

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry entry) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        journalService.addEntry(entry,username);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEntryById(@PathVariable String id) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        JournalEntry journalEntry= journalService.getEntryById(username,id);
        return journalEntry==null?
                ResponseEntity.notFound().build():
                ResponseEntity.ok(journalEntry);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JournalEntry> deleteEntryById(@PathVariable String id) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        journalService.deleteEntryById(id,username);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<JournalEntry> updateEntry(@PathVariable String id,
                                                    @RequestBody JournalEntry entry) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        JournalEntry updated = journalService.updateEntry(id, entry,username);
        return ResponseEntity.ok(updated);

    }
}
