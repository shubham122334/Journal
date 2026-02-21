package net.engineeringdigest.journalApp.cache;

import lombok.RequiredArgsConstructor;
import net.engineeringdigest.journalApp.repository.ConfigJournalAppRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AppCache {

    private final ConfigJournalAppRepository configJournalAppRepository;

    public Map<String, String> cache ;

    @PostConstruct
    public void initCache(){
        cache= new HashMap<>();
        configJournalAppRepository.findAll().forEach(e -> cache.put(e.getKey(), e.getValue()));
    }
}
