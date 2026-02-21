package net.engineeringdigest.journalApp.entity;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "config_journal_app")
public class ConfigJournalEntry {

    @Id
    private ObjectId id;
    private String key;
    private String value;


}
