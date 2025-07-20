package com.shabbir.Jwt_Impl.Controller;

import com.shabbir.Jwt_Impl.Entity.JournalEntry;
import com.shabbir.Jwt_Impl.Service.JournalService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalController {
    @Autowired
    JournalService journalService;

    @PostMapping
    public ResponseEntity<?> addNewEntry(@RequestBody JournalEntry newEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return journalService.addNewEntry(newEntry,username);
    }

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllEntry(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return journalService.getAllEntry(username);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updateSingleEntry(@RequestBody JournalEntry journalEntry, @PathVariable ObjectId id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return journalService.updateSingleEntry(journalEntry,username,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteSingleEntry(@PathVariable ObjectId id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return journalService.deleteSingleEntry(username,id);
    }

}
