package net.engineeringdigest.journalApp.scheduler;


import lombok.RequiredArgsConstructor;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepositoryImpl;
import net.engineeringdigest.journalApp.services.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MailScheduler {

    private final UserRepositoryImpl userRepository;
    private final EmailService emailService;
    private final AppCache appCache;

    //@Scheduled(cron = "0 */1 * * * *")
    public void fetchUserAndSendMail(){

        List<User> userList=userRepository.getUserForSA();
        for(User user:userList){
            List<JournalEntry> entries=user.getJournalEntries();
            List<JournalEntry> filtered=entries.stream().
                    filter(e->e.getCreatedDate().isAfter(LocalDateTime.now().minusDays(9)))
                    .collect(Collectors.toList());

            String journals=filtered.stream().map(JournalEntry::getContent)
                    .collect(Collectors.joining("\n"));

            String sentiments=filtered.stream().map(e->e.getSentiments().toString())
                            .collect(Collectors.joining(","));

            emailService.setMailMessage(user.getEmail(),"Journal | Sentiment Analysis",
                    "Hi "+user.getUsername()+"\n\n"+journals+
                            "\n\nLast 7-Days:["+sentiments+
                            "]\n\nRegards\nJournal App");
        }
    }

    @Scheduled(cron = "0 */10 * * * *")
    public void appCache(){
        appCache.initCache();
    }

}
