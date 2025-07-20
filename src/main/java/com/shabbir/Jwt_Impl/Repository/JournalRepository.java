package com.shabbir.Jwt_Impl.Repository;

import com.shabbir.Jwt_Impl.Entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface JournalRepository extends MongoRepository<JournalEntry, ObjectId> {
    List<JournalEntry> findByTitle(String title);
}
