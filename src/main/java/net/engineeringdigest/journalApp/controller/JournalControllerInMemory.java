package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntryInMemory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/journalTest")
public class JournalControllerInMemory {
    private final Map<Long, JournalEntryInMemory> entries = new HashMap<>();

    @GetMapping
    public List<JournalEntryInMemory> getAllEntries() {
        return new ArrayList<>(entries.values());
    }

    @PostMapping
    public ResponseEntity<String> addEntry(@RequestBody JournalEntryInMemory entry) {
       // Long id = entries.keySet().stream().max(Long::compareTo).orElse(0L) + 1;
        entries.put(entry.getId(), entry);
        return ResponseEntity.ok("Successfully added new journal entry");
    }

    @GetMapping("/{id}")
    public JournalEntryInMemory getEntryById(@PathVariable Long id) {
        return entries.get(id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEntryById(@PathVariable Long id) {
        if (entries.containsKey(id)) {
            entries.remove(id);
            return ResponseEntity.ok("Successfully deleted journal entry with id:"+id);
        }
        return new ResponseEntity<>("journal entry not found with id:"+id,HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEntry(@PathVariable Long id,@RequestBody JournalEntryInMemory entry) {
        if (entries.containsKey(id)) {
            entries.put(id, entry);
            return ResponseEntity.ok("Successfully updated journal entry with "+entries.get(id));
        }
        return new ResponseEntity<>("Can't update as No Entry found with id:"+id,HttpStatus.NOT_FOUND);

    }
}
