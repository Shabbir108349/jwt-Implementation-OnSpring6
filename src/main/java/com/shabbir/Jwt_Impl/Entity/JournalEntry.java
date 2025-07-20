package com.shabbir.Jwt_Impl.Entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "journalEntries")
public class JournalEntry {
    @Id
    private ObjectId id;
    private String title;
    private String content;
}
