package net.engineeringdigest.journalApp.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    void setMailMessage() {
        emailService.setMailMessage("Shubhamklp1999@gmail.com",
                "Journal App || Sentiment Analysis",
                "Hi Shubham\n\n" +
                        "Last month report for Sentiment Analysis is Happy\n" +
                        "Thank you for using our service" +
                        "\n\nRegards\nJournal App");
    }
}