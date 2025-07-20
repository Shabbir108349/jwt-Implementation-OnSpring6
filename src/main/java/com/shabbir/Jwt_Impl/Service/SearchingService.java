package com.shabbir.Jwt_Impl.Service;

import com.shabbir.Jwt_Impl.Entity.JournalEntry;
import com.shabbir.Jwt_Impl.Repository.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchingService {
    @Autowired
    JournalRepository journalRepository;
    
    public ResponseEntity<List<JournalEntry>> filteredEntry(String title){
        List<JournalEntry> findByTitle = journalRepository.findByTitle(title);
        return new ResponseEntity<>(findByTitle, HttpStatus.OK);
    }
    
}
