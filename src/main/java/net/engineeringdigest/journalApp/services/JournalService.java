package net.engineeringdigest.journalApp.services;

import lombok.RequiredArgsConstructor;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.exception.JournalEntryNotFoundException;
import net.engineeringdigest.journalApp.exception.UserException;
import net.engineeringdigest.journalApp.exception.UserNotFoundException;
import net.engineeringdigest.journalApp.repository.JournalRepository;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class JournalService {

    private final JournalRepository journalRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addEntry(JournalEntry entry,String user) {
        try {
            User u = userRepository.findByUsername(user);
            if(u==null){
                throw new UserNotFoundException("User not found with username: "+user);
            }
            JournalEntry jE=journalRepository.save(entry);
            u.getJournalEntries().add(jE);
            userRepository.save(u);
        } catch (Exception e) {
            throw new JournalEntryNotFoundException(e.getMessage()," Something went wrong");
        }
    }

    @Transactional
    public void deleteEntryById(String id, String username) {
        User user=userRepository.findByUsername(username);
        if(user==null){
            throw new UserNotFoundException("User not found with username: "+username);
        }
        JournalEntry entry = user.getJournalEntries().stream()
                .filter(j->j.getId().equals(id)).findFirst()
                .orElseThrow(() -> new JournalEntryNotFoundException("Journal entry not found or it does not belong to user: ",username));

        boolean removed = user.getJournalEntries()
                .removeIf(e -> e.getId().equals(id));

        if (!removed) {
            throw new JournalEntryNotFoundException("Journal entry not found or it does not belong to user: ",username);
        }
        userRepository.save(user);
        journalRepository.delete(entry);
    }

    @Transactional
    public JournalEntry updateEntry(String id,JournalEntry entry,String username) {
        List<JournalEntry> userJournalEntries=userRepository.findByUsername(username).getJournalEntries();
        JournalEntry jE=userJournalEntries.stream().filter(j -> j.getId()
                        .equals(id))
                .findFirst()
                .orElseThrow(() -> new JournalEntryNotFoundException("Journal entry not found by id:" + id, " for user:" + username));
        jE.setTitle(entry.getTitle());
        jE.setContent(entry.getContent());
        return journalRepository.save(jE);
    }

    @Transactional(readOnly = true)
    public List<JournalEntry> getAllUserEntries(String username) {
        User user = userRepository.findByUsername(username);
        if(user==null){
            throw new UserNotFoundException("User not found with username: "+username);
        }
        return user.getJournalEntries();
    }

    @Transactional(readOnly = true)
    public JournalEntry getEntryById(String username,String id) {
        User user = userRepository.findByUsername(username);
        if(user==null){
            throw new UserException("User not found with username: "+username);
        }
        return user.getJournalEntries().stream().filter(j -> j.getId()
                .equals(id))
                .findFirst()
                .orElseThrow(() -> new JournalEntryNotFoundException("Journal entry not found by id:" + id, " foe user:" + username));
    }

}
