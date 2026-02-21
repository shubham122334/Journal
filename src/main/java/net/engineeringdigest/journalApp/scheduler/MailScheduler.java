package net.engineeringdigest.journalApp.scheduler;


import lombok.RequiredArgsConstructor;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepositoryImpl;
import net.engineeringdigest.journalApp.services.EmailService;
import net.engineeringdigest.journalApp.services.SentimentAnalysis;
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
    private final SentimentAnalysis sentimentAnalysis;
    private final AppCache appCache;

   // @Scheduled(cron = "0 */1 * * * *")
    public void fetchUserAndSendMail(){

        List<User> userList=userRepository.getUserForSA();
        for(User user:userList){
            List<JournalEntry> entries=user.getJournalEntries();
            List<String> filtered=entries.stream().
                    filter(e->e.getCreatedDate().isAfter(LocalDateTime.now().minusDays(9)))
                    .map(e->e.getContent())
                    .collect(Collectors.toList());
            emailService.setMailMessage(user.getEmail(),"Journal App || Sentiment Analysis",
                    "Hi "+user.getUsername()+"\n"+
                    "\n"+ String.join("\n", filtered) +
                            "\n\nLast 7-Days:"+sentimentAnalysis.getSentiment()+
                            "\n\nRegards\nJournal App");
        }
    }

    @Scheduled(cron = "0 */10 * * * *")
    public void appCache(){
        appCache.initCache();
    }

}
