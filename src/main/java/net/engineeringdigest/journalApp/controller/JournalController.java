package net.engineeringdigest.journalApp.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import net.engineeringdigest.journalApp.entity.JournalEntry;

import net.engineeringdigest.journalApp.services.JournalService;
import net.engineeringdigest.journalApp.services.RedisService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;


@RestController
@RequiredArgsConstructor
@RequestMapping("/journal")
public class JournalController {

    private final JournalService journalService;
    private final RedisService redisService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllEntries() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();

        String cacheKey = "journal:" + username;
        List<JournalEntry> entries = redisService.get(cacheKey, new TypeReference<List<JournalEntry>>() {});

        if (entries != null && !entries.isEmpty()) {
            return ResponseEntity.ok(entries);
        }

        List<JournalEntry> journalEntries = journalService.getAllUserEntries(username);
        if (journalEntries.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        redisService.set(cacheKey, journalEntries, 600L);
        return ResponseEntity.ok(journalEntries);
    }

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry entry) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        journalService.addEntry(entry,username);
        redisService.delete("journal:" + username);
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
        redisService.delete("journal:" + username);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<JournalEntry> updateEntry(@PathVariable String id,
                                                    @RequestBody JournalEntry entry) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        JournalEntry updated = journalService.updateEntry(id, entry,username);
        redisService.delete("journal:" + username);
        return ResponseEntity.ok(updated);

    }
}
